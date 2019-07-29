package com.oecoo.user.common;

/**
 * Created by gf on 2018/4/26.
 */
public class Const {

    public static final String USER_NAME = "username";
    public static final String EMAIL = "email";

    public interface RedisCacheExTime {
        int REDIS_SESSION_EXTIME = 60 * 30;//session 过期时间
    }

    public interface TokenCache {
        String TOKEN_PREFIX = "token_";
    }

    /**
     * 组合常量在Const类中可用内部接口方式实现
     */
    public interface Role {
        int ROLE_CUSTOMER = 0;  //普通用户
        int ROLE_ADMIN = 1;     //管理员
    }

}
