package com.oecoo.order.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CartReceiverResult {

    String CLEAN_CARTS_RESULT = "clean_carts_result";

    @Input(CLEAN_CARTS_RESULT)
    SubscribableChannel clean_carts_result();
    
}
