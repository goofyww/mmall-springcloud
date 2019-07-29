package com.oecoo.order.mq.impl;

import com.oecoo.order.stream.OrderSender;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

/**
 * @author gf
 * @date 2019/7/28 13:51
 */
@Component
@EnableBinding(OrderSender.class)
public class OrderSenderImpl {
    
}
