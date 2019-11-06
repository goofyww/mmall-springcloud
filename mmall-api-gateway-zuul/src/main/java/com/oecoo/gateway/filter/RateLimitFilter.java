package com.oecoo.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import com.oecoo.toolset.common.ServerResponse;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 限流 filter
 * 限流 应该 放置在 请求被转发之前调用
 * 如果 前置中存在 限流 和 鉴权 ，那么 限流 应该早于 鉴权
 *
 * @author gf
 * @date 2019/8/2 16:08
 */
@Component
public class RateLimitFilter extends ZuulFilter {

    /**
     * 使用 google 令牌桶算法
     * 每秒放 100 个令牌
     **/
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    public String filterType() {
        return PRE_TYPE;
    }

    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public ServerResponse run() throws ZuulException {
        /* 如果没有拿到令牌，给调用者一个通知 */
        if (!RATE_LIMITER.tryAcquire()) return ServerResponse.createByErrorMessage("一大波人类正在接近，请稍后再试");
        return null;
    }
}
