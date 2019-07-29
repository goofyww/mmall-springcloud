package com.oecoo.cart.mq;

import com.oecoo.cart.entity.pojo.Cart;
import com.oecoo.cart.service.ICartService;
import com.oecoo.toolset.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gf
 * @date 2019/7/25 14:02
 */
//@Component
@Slf4j
public class CartReceiverV1 {

    @Autowired
    private ICartService iCartService;

    /**
     * 监听订单下单消息
     * 清空购物车
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("cleanCartList"),
            key = "cleanCartList",
            exchange = @Exchange("cartExchange")))
    public void cleanCartList(String message) {
        log.info("cart-service clean cartList start, message: {}", message);
        List<Cart> carts = JsonUtil.string2Obj(message, List.class, Cart.class);
        iCartService.cleanCarts(carts);
    }
}
