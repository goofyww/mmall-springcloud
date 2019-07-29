package com.oecoo.order.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oecoo.cart.client.CartClient;
import com.oecoo.cart.entity.pojo.Cart;
import com.oecoo.order.common.Const;
import com.oecoo.order.dao.OrderItemMapper;
import com.oecoo.order.dao.OrderMapper;
import com.oecoo.order.dao.PayInfoMapper;
import com.oecoo.order.entity.pojo.Order;
import com.oecoo.order.entity.pojo.OrderItem;
import com.oecoo.order.entity.pojo.PayInfo;
import com.oecoo.order.entity.transfer.OrderItemTransfer;
import com.oecoo.order.entity.transfer.OrderTransfer;
import com.oecoo.order.entity.vo.OrderItemVo;
import com.oecoo.order.entity.vo.OrderProductVo;
import com.oecoo.order.entity.vo.OrderVo;
import com.oecoo.order.service.IOrderService;
import com.oecoo.order.stream.OrderSender;
import com.oecoo.product.client.ProductClient;
import com.oecoo.product.entity.pojo.Product;
import com.oecoo.shipping.client.ShippingClient;
import com.oecoo.shipping.entity.pojo.Shipping;
import com.oecoo.shipping.entity.transfer.ShippingTransfer;
import com.oecoo.toolset.common.ResponseCode;
import com.oecoo.toolset.common.ServerResponse;
import com.oecoo.toolset.util.BigDecimalUtil;
import com.oecoo.toolset.util.DateTimeUtil;
import com.oecoo.toolset.util.FTPUtil;
import com.oecoo.toolset.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by gf on 2018/5/5.
 */
@Service("iOrderService")
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartClient cartClient;

    @Autowired
    private ShippingClient shippingClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private OrderSender orderSender;

    //创建订单
    public ServerResponse createOrder(Integer userId, Integer shippingId) {

        //从currentUser的购物车中获取数据 ,购物车状态需是已勾选的
        List<Cart> cartList = cartClient.selectCheckedCartByUserId(userId);

        //得到购物车中的商品详情
        ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();//订单详情集合
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);//订单总价

        //订单入库 两次写入操作 需要事务支持，因此将其单独抽离为一个方法
        ServerResponse<Order> orderResult = this.createOrder(userId, shippingId, orderItemList, payment);
        if (!orderResult.isSuccess()) {
            // 订单入库失败
            return orderResult;
        }
        //订单入库 成功
        //通知 updateProductQuantity MQ （product服务监听） 减少产品库存
        // v1.0 通过rabbitmq 发送消息
        // amqpTemplate.convertAndSend("productExchange", "decrProductQuantity", JsonUtil.obj2String(orderItemList));
        // v2.0 通过spring cloud stream 发送消息
        orderSender.decrProductCount().send(MessageBuilder.withPayload(orderItemList).build());

        //清空购物车
        // v1.0 通过rabbitmq 发送消息
        // amqpTemplate.convertAndSend("cartExchange", "cleanCartList", JsonUtil.obj2String(cartList));
        // v2.0 通过spring cloud stream 发送消息
        orderSender.cleanCarts().send(MessageBuilder.withPayload(cartList).build());

        //返回给前端数据
        OrderVo orderVo = this.assembleOrderVo(orderResult.getData(), orderItemList);
        return ServerResponse.createBySuccessData(orderVo);
    }

    @Transactional
    protected ServerResponse<Order> createOrder(Integer userId, Integer shippingId, List<OrderItem> orderItemList, BigDecimal payment) {
        Order order = new Order();
        long orderNo = this.generateOrderNo(); // 生成订单号
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());//在线支付
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);

        int orderInsertState = orderMapper.insert(order);
        if (order == null || orderInsertState == 0) {
            return ServerResponse.createByErrorMessage("生成订单失败");
        }
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //mybatis批量插入
        orderItemMapper.batchInsert(orderItemList);
        return ServerResponse.createBySuccessData(order);
    }

    //取消订单
    public ServerResponse cancel(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("该用户此订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            return ServerResponse.createByErrorMessage("已支付,无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());

        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (row > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    //获得购物车中商品信息
    public ServerResponse getOrderCartProduct(Integer userId) {

        OrderProductVo orderProductVo = new OrderProductVo();
        //从购物车中获取数据
        List<Cart> cartList = cartClient.selectCartByUserId(userId);
        ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        if (orderItemList == null) {
            return ServerResponse.createByErrorMessage("订单详情集合为空");
        }
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(OrderItemTransfer.toVo(orderItem));
        }

        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccessData(orderProductVo);
    }

    //订单列表
    public ServerResponse<PageInfo> getOrderlist(Integer userId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = this.assembleOrderVoList(userId, orderList);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);

        return ServerResponse.createBySuccessData(pageResult);
    }


    private List<OrderVo> assembleOrderVoList(Integer userId, List<Order> orderList) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList;
            if (userId == null) {
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderItemMapper.getByOrderNoAndUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    public ServerResponse getOrderDetail(Integer userId, Long orderNo) {
        if (orderNo == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoAndUserId(orderNo, userId);
            OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccessData(orderVo);
        }
        return ServerResponse.createByErrorMessage("未找到此订单");
    }


    //backend

    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVo> orderVoList = this.assembleOrderVoList(null, orderList);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return ServerResponse.createBySuccessData(pageResult);
    }

    public ServerResponse<OrderVo> manageDetail(Long orderNo) {
        if (orderNo == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccessData(orderVo);
        }
        return ServerResponse.createByErrorMessage("此订单不存在");
    }

    public ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (orderNo == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = this.assembleOrderVo(order, orderItemList);

            PageInfo pageResult = new PageInfo(Lists.newArrayList(order));
            pageResult.setList(Lists.newArrayList(orderVo));
            return ServerResponse.createBySuccessData(pageResult);
        }
        return ServerResponse.createByErrorMessage("此订单不存在");
    }

    public ServerResponse<String> manageSendGoods(Long orderNo) {
        if (orderNo == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                int result = orderMapper.updateByPrimaryKeySelective(order);
                if (result > 0) {
                    return ServerResponse.createBySuccessMessage("发货成功");
                }
                return ServerResponse.createByErrorMessage("发货失败");
            }
        }
        return ServerResponse.createByErrorMessage("此订单不存在");
    }

    /**
     * 关闭订单
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<List<OrderItem>> closeOrderProcess(int hour) {
        List<List<OrderItem>> resultOrderItemLists = Lists.newArrayList();
        Date closeDateTime = DateUtils.addHours(new Date(), -hour); //指定hour时间之前的订单需关闭
        // 将 order 表 中已过期的 数据集合 拿到
        List<Order> orderList = orderMapper.selectOrderStatusByCreateTime(
                Const.OrderStatusEnum.NO_PAY.getCode(),
                DateTimeUtil.dateToStr(closeDateTime));
        for (Order order : orderList) {
            // 遍历 order集合 ，获取 每个订单的订单详情
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            resultOrderItemLists.add(orderItemList);
            orderMapper.closeOrderByOrderId(order.getId());
            log.info("关闭订单 OrderNo: {}", order.getOrderNo());
        }
        return resultOrderItemLists;
    }

    public void closeOrder(int hour) {
        //将需要恢复的商品集合通过 mq 发送给 product-service
        // 1.0 通过rabbitmq 发送消息
        // amqpTemplate.convertAndSend("productExchange", "incrProductQuantity", JsonUtil.obj2String(orderItemLists));
        // 2.0 通过spring cloud stream 发送消息
        orderSender.incrProductCount().send(MessageBuilder.withPayload(closeOrderProcess(hour)).build());
    }

    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = OrderTransfer.toVO(order);
        //支付类型描述
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());
        //订单状态描述
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        Shipping shipping = shippingClient.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(ShippingTransfer.toVo(shipping));
        }
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = OrderItemTransfer.toVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    //订单号生成规则
    private long generateOrderNo() {
        long currentTimes = System.currentTimeMillis();
        return currentTimes + new Random().nextInt(100);
    }

    //获得订单总价
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    //获得 购物车中产品详情集合
    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {

        List<OrderItem> orderItemList = Lists.newArrayList();

        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        //遍历购物车集合
        for (Cart cartItem : cartList) {
            OrderItem orderItem = new OrderItem();
            Product product = productClient.selectByPrimaryKey(cartItem.getProductId());

            //获得商品 查看商品是否为在线状态
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                //不在线
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "不在售卖状态");
            }
            //校验 库存
            if (cartItem.getQuantity() > product.getStock()) {
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "库存不足");
            }
            //开始拼装 订单详情
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductImage(product.getMainImage());  //主图
            orderItem.setCurrentUnitPrice(product.getPrice());  //当前价格
            //总价
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity().doubleValue()));

            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccessData(orderItemList);
    }


    /**
     * 支付宝支付
     *
     * @param orderNo 订单id
     * @param userId  用户id
     * @param path    上下文 文件夹路径
     * @return
     */
    public ServerResponse pay(Long orderNo, Integer userId, String path) {
        //map 用来存放订单信息
        Map<String, String> resultMap = Maps.newHashMap();
        //by userId orderId 查询该订单是否存在
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            //不存在 返回前台错误信息
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        //存在
        //map .put  订单编号
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));

        /*-------------------------支付宝接口Demo指定格式---------------------------------*/

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("happlymmall扫码支付，订单号:").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        // by userId orderNo 查询订单详情集合（商品、商品数量、商品价格、总价）
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoAndUserId(orderNo, userId);
        //遍历订单详情集合，将每种商品(订单详情)加入到 支付宝规定的集合中
        for (OrderItem orderItem : orderItemList) {

            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    //传入Long类型之后 ，支付宝会再进行一次除 100的运算 ,得到小数点后两位（毛、分）
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100)).longValue(), orderItem.getQuantity());
            //将当前商品加入 支付宝的商品集合
            goodsDetailList.add(goods);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //商户的回调地址
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                //商品集合
                .setGoodsDetailList(goodsDetailList);

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                // 需要修改为运行机器上的路径
                // 注意 路径后面+/               qr-商户订单号.png           商户订单号
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());//本地路径+文件
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());//文件

                /*支付宝 生成二维码 Start
                 *生成文件位置在 本地 qrPath
                 */
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
                log.info("------二维码生成success------");

                //本地二维码
                File targetFile = new File(path, qrFileName);
                try {
                    //上传二维码到 文件服务器
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));

                } catch (IOException e) {
                    log.error("上传二维码异常", e);
                    return null;
                }

                log.info("FTPfilePath:" + targetFile);
                //拼接远程服务器 二维码地址
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
                // map.put 这个远程地址
                resultMap.put("qrUrl", qrUrl);

                //此时二维码已存在文件服务器上，需要删除本地二维码，否则会占本地空间
                targetFile.delete();

                return ServerResponse.createBySuccessData(resultMap);//返回给前台这个 resultMap
            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");
            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");
            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    /**
     * 支付宝回调
     *
     * @param params
     * @return
     */
    public ServerResponse aliCallback(Map<String, String> params) {

        Long orderNo = Long.parseLong(params.get("out_trade_no"));  //商户订单号
        String tradeNo = params.get("trade_no");                    //支付宝交易号
        String tardeStatus = params.get("trade_status");            //交易状态
        //验证订单是否存在
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("非mmall商城的订单,回调忽略");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            //这里使用success ,不是success 支付宝会一直重复调用
            return ServerResponse.createBySuccessMessage("支付宝重复调用");
        }
        //如果交易成功
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tardeStatus)) {
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));//订单交易时间
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());//订单状态置为已付款
            orderMapper.updateByPrimaryKeySelective(order);//修改订单状态
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tardeStatus);

        payInfoMapper.insert(payInfo);

        return ServerResponse.createBySuccess();
    }

    public ServerResponse<Boolean> queryOrderPayStatus(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("用户没有此订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

}
