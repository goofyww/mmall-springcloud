package com.oecoo.user.controller.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gf
 * @date 2019/7/8 0:04
 */
@RestController
@RequestMapping("/env/")
@RefreshScope
public class EnvController {

    @Value("${env}")
    private String envStr;

    @Autowired
    private UserEnv userEnv;

    // 测试 是否可以获取到 git上的配置信息
    @GetMapping("get")
    public String getStr() {
        return envStr;
    }

    @GetMapping("info")
    public String getPrefix() {
        return "name:" + userEnv.getName() + "\t age:" + userEnv.getAge();
    }

}
