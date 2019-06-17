package com.bitmall.controller.portal;

import com.bitmall.common.Const;
import com.bitmall.common.ResponseCode;
import com.bitmall.common.ServerResponse;
import com.bitmall.dataobject.UserDO;
import com.bitmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author liuyuehe
 * @date 2019/6/9 10:17
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    /**
     * 使用接口注入可以解耦合，UserServiceImpl在添加@Service的时候添加了iUserService，因此能够正确添加
     */
    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse<UserDO> login(String username, String password, HttpSession session) {
        ServerResponse<UserDO> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public ServerResponse register(UserDO userDO) {
        return iUserService.register(userDO);
    }

    @RequestMapping(value = "check_valid", method = RequestMethod.POST)
    public ServerResponse checkValid(String val, String type) {
        return iUserService.checkValid(val, type);
    }

    @RequestMapping(value = "get_user_info", method = RequestMethod.GET)
    public ServerResponse getUserInfo(HttpSession session) {
        UserDO userDO = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (userDO != null) {
            return ServerResponse.createBySuccess(userDO);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取用户信息");
    }

    @RequestMapping(value = "forget_get_question", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    @RequestMapping(value = "forget_check_answer", method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    @RequestMapping(value = "forget_reset_password", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String token) {
        return iUserService.forgetResetPassword(username, passwordNew, token);
    }

    @RequestMapping(value = "reset_password", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, HttpSession session) {
        UserDO userDO = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (userDO == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, userDO, passwordNew);
    }

    @RequestMapping(value = "update_information", method = RequestMethod.POST)
    public ServerResponse<UserDO> updateUserInformation(HttpSession session, UserDO userDO) {
        UserDO currentUserDO = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (currentUserDO == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        // 这儿也是做了一个防止横向越权的操作
        userDO.setId(currentUserDO.getId());
        userDO.setUsername(currentUserDO.getUsername());
        ServerResponse<UserDO> response = iUserService.updateUserInformation(userDO);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "get_information", method = RequestMethod.GET)
    public ServerResponse<UserDO> getInformation(HttpSession session) {
        UserDO currentUser = (UserDO) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要强制登录");
        }
        return iUserService.getUserInformation(currentUser.getId());
    }
}
