package com.oecoo.order.entity.transfer;

import com.oecoo.order.entity.pojo.OrderItem;
import com.oecoo.order.entity.vo.OrderItemVo;
import com.oecoo.toolset.util.DateTimeUtil;

/**
 * @author gf
 * @date 2019/6/30 20:49
 */
public class OrderItemTransfer {

    //转orderItemVO
    public static OrderItemVo toVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }
}
