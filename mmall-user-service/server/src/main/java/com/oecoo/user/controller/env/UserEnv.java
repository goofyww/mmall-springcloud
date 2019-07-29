package com.oecoo.user.controller.env;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author gf
 * @date 2019/7/8 0:07
 */
@Data
@Component
@ConfigurationProperties("user")
@RefreshScope
public class UserEnv {

    private String name;

    private String age;

}
