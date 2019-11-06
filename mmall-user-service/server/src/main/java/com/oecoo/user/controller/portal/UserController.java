package com.oecoo.user.controller.portal;

import com.oecoo.toolset.common.CookieConst;
import com.oecoo.toolset.common.ResponseCode;
import com.oecoo.toolset.common.ServerResponse;
import com.oecoo.toolset.util.CookieUtil;
import com.oecoo.toolset.util.JsonUtil;
import com.oecoo.toolset.util.RedisShardedPoolUtil;
import com.oecoo.user.common.Const;
import com.oecoo.user.entity.pojo.User;
import com.oecoo.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author gf
 * @date 2019/6/8
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping(value = "login.do")
    public ServerResponse<User> login(String username, String password, HttpServletResponse httpServletResponse, HttpSession session) {

        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            // 将登录用户信息存入redis，有效时间为30分钟
            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
            // 写入 Cookie
            CookieUtil.writeLoginToken(httpServletResponse, CookieConst.LOGIN_TOKEN, session.getId());
            return ServerResponse.createBySuccessData(response.getData());
        }
        return response;
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping(value = "logout.do")
    public ServerResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String loginToken = CookieUtil.readLoginToken(request, CookieConst.LOGIN_TOKEN);
        CookieUtil.delLoginToken(request, response, CookieConst.LOGIN_TOKEN);
        RedisShardedPoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    /**
     * 新用户注册
     *
     * @param user
     * @return
     */
    @PostMapping(value = "register.do")
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 校验
     *
     * @param str  前台传过来的 value
     * @param type 判断 是     email 还是 username
     * @return
     */
    @PostMapping(value = "check_valid.do")
    public ServerResponse<String> chekcValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取当前登录用户信息
     * 简略信息 只从session获取一下
     *
     * @return
     */
    @PostMapping(value = "get_user_info.do")
    public ServerResponse<User> getUserInfo(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request, CookieConst.LOGIN_TOKEN);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user != null) {
            return ServerResponse.createBySuccessData(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    /**
     * 获取当前用户的用户问题
     *
     * @param username
     * @return
     */
    @PostMapping(value = "forget_get_question.do")
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 检查问题答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @PostMapping(value = "forget_check_answer.do")
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 未登录 修改密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @PostMapping(value = "forget_rest_password.do")
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.beforeLoginRestPassword(username, passwordNew, forgetToken);
    }

    /**
     * 已登录 修改密码
     *
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @PostMapping(value = "rest_password.do")
    public ServerResponse<String> restPassword(HttpServletRequest request, String passwordOld, String passwordNew) {
        String loginToken = CookieUtil.readLoginToken(request, CookieConst.LOGIN_TOKEN);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("当前用户未登录");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("当前用户未登录");
        }
        return iUserService.loginRestPassword(passwordOld, passwordNew, user);
    }

    /**
     * 登录状态更新个人信息
     *
     * @param user
     * @return
     */
    @PostMapping(value = "update_information.do")
    public ServerResponse<User> updateInformation(HttpServletRequest request, User user) {
        String loginToken = CookieUtil.readLoginToken(request, CookieConst.LOGIN_TOKEN);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("当前用户未登录");
        }
        User currentUser = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("当前用户未登录");
        }
        /*
         * 为防止越权问题 ，将当前session中的currentUserId赋值为userId
         * 目的: 需要修改的用户信息只能是当前这个session用户的信息 ，其它用户的信息无法修改
         */
        // user 从前台传过来的时候，只包含它需要修改的信息, 因此将session中id和username赋值给user，之后再进行service操作
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            //更新成功返回的信息不包括name ,因此重新指定返回的username是当前session中的username
            response.getData().setUsername(currentUser.getUsername());
            RedisShardedPoolUtil.setEx(loginToken, JsonUtil.obj2String(response.getData()), Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 获取当前登录用户的详细信息
     * 详细信息通过userId去数据库查询
     *
     * @return
     */
    @PostMapping(value = "get_information.do")
    public ServerResponse<User> getInformation(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request, CookieConst.LOGIN_TOKEN);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("当前用户未登录");
        }
        User currentUser = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "当前用户未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }

}
