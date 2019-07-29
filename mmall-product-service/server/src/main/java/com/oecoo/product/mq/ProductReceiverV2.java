package com.oecoo.product.mq;

import com.oecoo.order.entity.pojo.OrderItem;
import com.oecoo.order.stream.ProductReceiver;
import com.oecoo.product.service.IProductService;
import com.oecoo.toolset.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gf
 * @date 2019/7/27 19:01
 */
@Component
@EnableBinding(ProductReceiver.class)
@Slf4j
public class ProductReceiverV2 {

    @Autowired
    private IProductService iProductService;

    /**
     * 监听 订单服务下单 消息，
     * 对对应的订单商品在数据库中进行减库存操作
     *
     * @param orderItemList
     * @return
     */
    @StreamListener(ProductReceiver.DECR_PRODUCT_COUNT)
    @SendTo(ProductReceiver.DECR_PRODUCT_COUNT_RESULT)
    public boolean decrProductQuantityReceiver(List<OrderItem> orderItemList) {
        log.info("decrProductQuantityReceiver: {}", JsonUtil.obj2String(orderItemList));
        return iProductService.decrProductQuantity(orderItemList).isSuccess();
    }

    /**
     * 订单关闭 恢复库存 +库存
     *
     * @param orderItemLists
     * @return
     */
    @StreamListener(ProductReceiver.INCR_PRODUCT_COUNT)
    @SendTo(ProductReceiver.INCR_PRODUCT_COUNT_RESULT)
    public boolean incrProductQuantityReceiver(List<List<OrderItem>> orderItemLists) {
        log.info("incrProductQuantityReceiver: {}", JsonUtil.obj2String(orderItemLists));
        return iProductService.incrProductQuantity(orderItemLists).isSuccess();
    }

}
