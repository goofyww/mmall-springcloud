package com.oecoo.order.mq.impl;

import com.oecoo.order.stream.CartReceiverResult;
import com.oecoo.order.stream.ProductReceiverResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author gf
 * @date 2019/7/28 15:49
 */
@Component
@EnableBinding(value = {ProductReceiverResult.class, CartReceiverResult.class})
@Slf4j
public class OrderReceiverImpl {

    /**
     * 减库存 队列 的 响应结果
     * 成功 ：已完成
     * 失败 ：订单事务回滚
     *
     * @param message
     */
    @StreamListener(ProductReceiverResult.DECR_PRODUCT_COUNT_RESULT)
    public void decrProductCountResult(String message) {
        log.info("OrderReceiverImpl decrProductCountResult: {}", message);

    }

    /**
     * 关闭订单恢复商品库存 队列 的 响应结果
     * 成功 ：已完成
     * 失败 ：失败重试
     *
     * @param message
     */
    @StreamListener(ProductReceiverResult.INCR_PRODUCT_COUNT_RESULT)
    public void incrProductCountResult(String message) {
        log.info("OrderReceiverImpl incrProductCountResult: {}", message);

    }

    /**
     * 清空购物车 队列 的 响应结果
     * 成功 ：已完成
     * 失败 ：失败重试
     *
     * @param message
     */
    @StreamListener(CartReceiverResult.CLEAN_CARTS_RESULT)
    public void cleanCartsResult(String message) {
        log.info("OrderReceiverImpl cleanCartsResult: {}", message);
    }

}
