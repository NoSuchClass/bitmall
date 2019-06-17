package com.bitmall.controller.backend;

import com.bitmall.common.Const;
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
 * @date 2019/6/10 9:07
 */
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {
    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse login(String username, String password, HttpSession session) {
        ServerResponse<UserDO> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            UserDO userDO = response.getData();
            if (userDO.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, userDO);
                return ServerResponse.createBySuccess(userDO);
            } else {
                return ServerResponse.createByErrorMessage("该操作需要管理员权限");
            }
        }
        return response;
    }
}
