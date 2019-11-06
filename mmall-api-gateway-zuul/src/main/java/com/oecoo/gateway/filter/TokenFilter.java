package com.oecoo.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * zuul pre filter example  前置
 *
 * @author gf
 * @date 2019/8/1 22:27
 */
//@Component
public class TokenFilter extends ZuulFilter {

    /**
     * filter 类型
     *
     * @return
     */
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * filter执行的优先级
     * 值越小 ，优先级越高
     *
     * @return
     */
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        /** 获取当前的上下文 **/
        RequestContext requestContext = RequestContext.getCurrentContext();
        /** 获取当前请求的 request对象 **/
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        String token = httpServletRequest.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            /** zuul route 不通过 **/
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
