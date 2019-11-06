package com.oecoo.gateway.filter;

import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.oecoo.toolset.common.CookieConst;
import com.oecoo.toolset.common.ServerResponse;
import com.oecoo.toolset.util.CookieUtil;
import com.oecoo.toolset.util.JsonUtil;
import com.oecoo.toolset.util.RedisShardedPoolUtil;
import com.oecoo.user.common.Const;
import com.oecoo.user.entity.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 管理端 前置 拦截器
 * 鉴权
 *
 * @author gf
 * @date 2019/8/3 14:10
 */
@Component
@Slf4j
public class AuthManageFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        /**
         * api 管理员 可访问
         * api 买家   可访问
         * api        都可访问
         *
         */
        String url = request.getRequestURI();
        // /manage/user/login.do   放行
        if (StringUtils.equals(url, "/user/manage/user/login.do")) {
            // 如果是登陆 url 则放行
            return false;
        }
        // /**/manage/**           进行拦截鉴权
        if (url.matches("(.*)/manage/(.*)")) {
            //正则匹配
            // 如果是包含 manage 内容的url 则进行过滤拦截
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();

        String routeURL = request.getRequestURI();

        //解析参数，具体的参数key以及value是什么，用作打印日志
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;

            //request这个参数返回的map,里面的value返回的是一个string[]
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        log.info("权限拦截器拦截到请求 url:{},param:{}", routeURL, requestParamBuffer);

        User user = null;
        String loginToken = CookieUtil.readLoginToken(request, CookieConst.LOGIN_TOKEN);
        if (StringUtils.isNotEmpty(loginToken)) {
            user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        }
        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            // 返回false,即不会调用controller中的方法
            if (user == null) {
                if (StringUtils.equals(routeURL, "/product/manage/product/richtext_img_upload.do")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "用户未登录,请先登陆");
                    send(JsonUtil.obj2String(resultMap), response);

                } else {
                    send(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")), response);
                }
            } else {
                if (StringUtils.equals(routeURL, "/product/manage/product/richtext_img_upload.do")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作，需要管理员权限");
                    send(JsonUtil.obj2String(resultMap), response);
                } else {
                    send(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户无权限访问")), response);
                }
            }
            // 拦截
        }
        // 放行
        return null;
    }

    /**
     * 重置response 用于向前台发送数据
     *
     * @param obj
     * @param response
     */
    private void send(Object obj, HttpServletResponse response) {
        response.reset(); // 重置response
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(obj);
            out.flush();
        } catch (IOException e) {
            log.error("ResponseResetOut send方法发生异常 {}", e);
        }
    }

    public static void main(String[] args) {
        String word = "/product/manage/product/list.do";
        boolean result = word.matches("(.*)/manage/(.*)");
        System.out.println(result);
    }

}
