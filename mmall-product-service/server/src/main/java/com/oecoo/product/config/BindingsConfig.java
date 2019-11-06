package com.oecoo.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.stereotype.Component;

/**
 * @author gf
 * @date 2019/8/1 2:15
 */
@Component
public class BindingsConfig {

    @ConfigurationProperties("spring.cloud.stream")
    @RefreshScope
    public BindingServiceProperties BindingServiceProperties() {
        return new BindingServiceProperties();
    }
}
