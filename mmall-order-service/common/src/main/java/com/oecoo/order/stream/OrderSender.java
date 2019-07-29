package com.oecoo.order.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Spring-Cloud-Stream 发送者
 */
public interface OrderSender {

    /*队列名*/
    String DECR_PRODUCT_COUNT_MESSAGE = "decr_product_count";

    String INCR_PRODUCT_COUNT_MESSAGE = "incr_product_count";

    String CLEAN_CARTS = "clean_carts";

    @Output(DECR_PRODUCT_COUNT_MESSAGE)
    MessageChannel decrProductCount();

    @Output(INCR_PRODUCT_COUNT_MESSAGE)
    MessageChannel incrProductCount();

    @Output(CLEAN_CARTS)
    MessageChannel cleanCarts();

}
