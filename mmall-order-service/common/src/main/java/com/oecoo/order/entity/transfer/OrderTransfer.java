package com.oecoo.order.entity.transfer;

import com.oecoo.order.entity.pojo.Order;
import com.oecoo.order.entity.vo.OrderVo;
import com.oecoo.toolset.util.DateTimeUtil;
import com.oecoo.toolset.util.PropertiesUtil;

/**
 * @author gf
 * @date 2019/6/30 17:45
 */
public class OrderTransfer {

    public static Order toPO(OrderVo vo) {
        Order order = new Order();

        order.setOrderNo(vo.getOrderNo());
        order.setShippingId(vo.getShippingId());
        order.setPostage(vo.getPostage());
        order.setStatus(vo.getStatus());
        order.setPayment(vo.getPayment());
        order.setPaymentType(vo.getPaymentType());
        order.setPaymentTime(DateTimeUtil.strToDate(vo.getPaymentTime()));
        order.setSendTime(DateTimeUtil.strToDate(vo.getSendTime()));
        order.setEndTime(DateTimeUtil.strToDate(vo.getEndTime()));
        order.setCloseTime(DateTimeUtil.strToDate(vo.getCloseTime()));
        order.setCreateTime(DateTimeUtil.strToDate(vo.getCreateTime()));
        return order;
    }

    public static OrderVo toVO(Order po) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(po.getOrderNo());
        orderVo.setPayment(po.getPayment());
        orderVo.setPostage(po.getPostage());
        //支付类型
        orderVo.setPaymentType(po.getPaymentType());
        //订单状态
        orderVo.setStatus(po.getStatus());
        orderVo.setShippingId(po.getShippingId());
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(po.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(po.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(po.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(po.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(po.getCloseTime()));
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return orderVo;
    }
}
