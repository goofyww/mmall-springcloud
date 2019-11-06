package com.oecoo.user.controller.backend;

import com.oecoo.toolset.common.CookieConst;
import com.oecoo.toolset.common.ServerResponse;
import com.oecoo.toolset.util.CookieUtil;
import com.oecoo.toolset.util.JsonUtil;
import com.oecoo.toolset.util.RedisShardedPoolUtil;
import com.oecoo.user.common.Const;
import com.oecoo.user.entity.pojo.User;
import com.oecoo.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author gf
 * @date 2019/6/8
 */
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @PostMapping(value = "login.do")
    public ServerResponse<User> login(HttpServletResponse httpServletResponse, HttpSession session, String username, String password) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            // 用户名和密码 有效
            User user = response.getData();
            //Role 1 管理员
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                // 写入 Cookie
                CookieUtil.writeLoginToken(httpServletResponse, CookieConst.LOGIN_TOKEN, session.getId());
                // 将登录用户信息存入redis，有效时间为30分钟
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        // 用户名和密码 无效，登录失败
        return response;
    }

}
