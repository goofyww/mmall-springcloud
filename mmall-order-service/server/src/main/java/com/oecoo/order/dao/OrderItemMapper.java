package com.oecoo.order.dao;

import com.oecoo.order.entity.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderItemMapper")
public interface OrderItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByOrderNo(@Param(value = "orderNo") Long orderNo);

    List<OrderItem> getByOrderNoAndUserId(@Param(value = "orderNo") Long orderNo, @Param(value = "userId") Integer userId);

    void batchInsert(@Param(value = "orderItemList") List<OrderItem> orderItemList);

}