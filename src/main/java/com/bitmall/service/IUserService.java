package com.bitmall.service;

import com.bitmall.common.ServiceResponse;
import com.bitmall.dataobject.UserDO;

import javax.servlet.http.HttpSession;

/**
 * @author liuyuehe
 * @date 2019/6/9 10:17
 */
public interface IUserService {
    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     */
    ServiceResponse<UserDO> login(String username, String password);

    /**
     * 注册接口
     *
     * @param userDO
     * @return
     */
    ServiceResponse<String> register(UserDO userDO);

    /**
     * 参数校验
     *
     * @param val  参数值
     * @param type 参数类型
     * @return
     */
    ServiceResponse<String> checkValid(String val, String type);

    /**
     * 通过用户名查询密保问题
     *
     * @param username
     * @return
     */
    ServiceResponse<String> selectQuestion(String username);

    /**
     * 检查密码是否正确
     * @param username
     * @param question
     * @param anwser
     * @return
     */
    ServiceResponse<String> checkAnswer(String username, String question, String anwser);

    /**
     * 用户忘记密码找回
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServiceResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 用户登录状态进行修改密码
     * @param passwordOld
     * @param userDO
     * @param passwordNew
     * @return
     */
    ServiceResponse<String> resetPassword(String passwordOld, UserDO userDO, String passwordNew);

    /**
     * 更新用户信息
     * @param userDO
     * @return
     */
    ServiceResponse<UserDO> updateUserInformation(UserDO userDO);

    ServiceResponse<UserDO> getUserInformation(Integer id);

    ServiceResponse checkAdminRole(UserDO user);
}
