package com.oecoo.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;

/**
 * @author gf
 * @date 2019/8/1 0:12
 */
@Component
public class ZuulConfig {

    /**
     * 动态路由
     * 使用 git 实时刷新 线上配置信息，
     * 避免了新增 Api时，重启服务
     *
     * @return
     */
    @ConfigurationProperties("zuul")
    @RefreshScope
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }

}
