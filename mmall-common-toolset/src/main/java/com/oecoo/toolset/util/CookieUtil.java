package com.oecoo.toolset.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kay on 2018/5/16.
 * 对 cookie 操作的封装
 */
@Slf4j
public class CookieUtil {

    /**
     * 写入login cookie
     *
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String cookieKey, String token) {
        Cookie cookie = new Cookie(cookieKey, token);
        cookie.setDomain(PropertiesUtil.getProperty("cookie.domain", "oecoo.com"));
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        //设置有效期为7天,不设置maxAge 只会存在于内存，不会写入硬盘
        cookie.setMaxAge(60 * 60 * 24 * 7);
        log.info("write cookieName:{} ,cookieValue:{}", cookie.getName(), cookie.getValue());
        response.addCookie(cookie);
    }

    /**
     * 读取 login cookie
     *
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request, String token) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), token)) {
                    log.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response, String cookieKey) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), cookieKey)) {
                    ck.setDomain(PropertiesUtil.getProperty("cookie.domain", "oecoo.com"));
                    ck.setPath("/");
                    ck.setMaxAge(0); //立即删除cookie

                    log.info("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}