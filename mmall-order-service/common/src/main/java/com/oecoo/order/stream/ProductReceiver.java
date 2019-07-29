package com.oecoo.order.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Spring-Cloud-Stream 接收者
 */
public interface ProductReceiver {

    /*队列名*/
    String DECR_PRODUCT_COUNT = "decr_product_count";

    String DECR_PRODUCT_COUNT_RESULT = "decr_product_count_result";

    String INCR_PRODUCT_COUNT = "incr_product_count";

    String INCR_PRODUCT_COUNT_RESULT = "incr_product_count_result";

    @Input(DECR_PRODUCT_COUNT)
    SubscribableChannel decrProductCount();

    @Output(DECR_PRODUCT_COUNT_RESULT)
    MessageChannel decrProductCountResult();

    @Input(INCR_PRODUCT_COUNT)
    SubscribableChannel incrProductCount();

    @Output(INCR_PRODUCT_COUNT_RESULT)
    MessageChannel incrProductCountResult();

}
