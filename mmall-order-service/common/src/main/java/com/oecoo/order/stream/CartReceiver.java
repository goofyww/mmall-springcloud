package com.oecoo.order.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CartReceiver {

    String CLEAN_CARTS = "clean_carts";

    String CLEAN_CARTS_RESULT = "clean_carts_result";

    @Input(CLEAN_CARTS)
    SubscribableChannel cleanCarts();

    @Output(CLEAN_CARTS_RESULT)
    MessageChannel cleanCartsResult();

}
