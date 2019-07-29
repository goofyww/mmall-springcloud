package com.oecoo.product.mq;

import com.oecoo.order.entity.pojo.OrderItem;
import com.oecoo.product.service.IProductService;
import com.oecoo.toolset.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gf
 * @date 2019/7/23 23:44
 */
//@Component
@Slf4j
public class ProductReceiverV1 {

    @Autowired
    private IProductService iProductService;

    /**
     * 监听 订单服务下单 消息，
     * 对对应的订单商品在数据库中进行减库存操作
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("decrProductQuantity"),
            key = "decrProductQuantity",
            exchange = @Exchange("productExchange")))
    public void decrProductQuantity(String message) {
        log.info("product-service decrProductQuantity listener message : {}", message);
        List<OrderItem> orderItems = JsonUtil.string2Obj(message, List.class, OrderItem.class);
        iProductService.decrProductQuantity(orderItems);
    }

    /**
     * 订单关闭 恢复库存 +库存
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("incrProductQuantity"),
            key = "incrProductQuantity",
            exchange = @Exchange("productExchange")))
    public void incrProductQuantity(String message) {
        log.info("product-service incrProductQuantity listener message : {}", message);
        List<List<OrderItem>> orderItemLists = JsonUtil.string2Obj(message,
                new TypeReference<List<List<OrderItem>>>() {
                });
        iProductService.incrProductQuantity(orderItemLists).isSuccess();
    }

}
