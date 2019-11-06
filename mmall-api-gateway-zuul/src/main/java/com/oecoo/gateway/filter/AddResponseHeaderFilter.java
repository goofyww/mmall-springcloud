package com.oecoo.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 * zuul post filter example 后置
 *
 * @author gf
 * @date 2019/8/1 23:35
 */
//@Component
public class AddResponseHeaderFilter extends ZuulFilter {

    public String filterType() {
        return POST_TYPE;
    }

    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();
        response.addHeader("X-Zuul", UUID.randomUUID().toString());
        return null;
    }
}
