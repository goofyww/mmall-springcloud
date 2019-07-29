package com.oecoo.order.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProductReceiverResult {

    String DECR_PRODUCT_COUNT_RESULT = "decr_product_count_result";

    String INCR_PRODUCT_COUNT_RESULT = "incr_product_count_result";

    @Input(DECR_PRODUCT_COUNT_RESULT)
    SubscribableChannel decr_product_count_message_result();

    @Input(INCR_PRODUCT_COUNT_RESULT)
    SubscribableChannel incr_product_count_message_result();

}
