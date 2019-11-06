package com.oecoo.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringCloudApplication
@EnableScheduling
@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.oecoo.*.client")
@ComponentScan(basePackages = {"com.oecoo"})
@MapperScan("com.oecoo.order.dao")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
