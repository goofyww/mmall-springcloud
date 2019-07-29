package com.oecoo.cart.mq;

import com.oecoo.cart.entity.pojo.Cart;
import com.oecoo.cart.service.ICartService;
import com.oecoo.order.stream.CartReceiver;
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
 * @date 2019/7/28 22:14
 */
@Component
@EnableBinding(CartReceiver.class)
@Slf4j
public class CartReceiverV2 {

    @Autowired
    private ICartService iCartService;

    @StreamListener(CartReceiver.CLEAN_CARTS)
    @SendTo(CartReceiver.CLEAN_CARTS_RESULT)
    public boolean cleanCartsReceiver(List<Cart> carts) {
        log.info("cleanCartsReceiver: {}", JsonUtil.obj2String(carts));
        return iCartService.cleanCarts(carts).isSuccess();
    }

}
