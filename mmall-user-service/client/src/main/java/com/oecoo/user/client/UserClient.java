package com.oecoo.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "mmall-user-service", fallback = UserClient.UserClientFallback.class)
public interface UserClient {

    @Component
    static class UserClientFallback implements UserClient {

    }
}
