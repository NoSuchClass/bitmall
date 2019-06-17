package com.bitmall.service.impl;

import com.bitmall.common.Const;
import com.bitmall.common.ServerResponse;
import com.bitmall.common.TokenCache;
import com.bitmall.dao.UserMapper;
import com.bitmall.dataobject.UserDO;
import com.bitmall.service.IUserService;
import com.bitmall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * @author liuyuehe
 * @date 2019/6/9 10:22
 */
@Service(value = "iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        UserDO userDO = userMapper.selectLogin(username, md5Password);
        if (userDO == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        userDO.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", userDO);
    }

    @Override
    public ServerResponse<String> register(UserDO userDO) {
        ServerResponse<String> response = checkValid(userDO.getUsername(), Const.USERNAME);
        if (!response.isSuccess()) {
            return response;
        }
        response = checkValid(userDO.getEmail(), Const.EMAIL);
        if (!response.isSuccess()) {
            return response;
        }

        userDO.setRole(Const.Role.ROLE_CUSTOMER);
        userDO.setPassword(MD5Util.MD5EncodeUtf8(userDO.getPassword()));
        int resultCount = userMapper.insert(userDO);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    /**
     * 用以校验参数是否正确
     *
     * @param val  参数值
     * @param type 参数类型（通过该参数进行需要校验的类型判断，如Const.USERNAME， Const.EMAIL）
     * @return 如果用户名不存在，那么将会校验成功。如果邮箱不存在，那么将会校验成功。
     */
    @Override
    public ServerResponse<String> checkValid(String val, String type) {
        /* todo:策略模式进行优化当前代码 */
        if (StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(val);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(val);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse<String> validResponse = checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("密保问题为空");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String anwser) {
        int resultCount = userMapper.checkAnswer(username, question, anwser);
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PERFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，token未传递");
        }
        ServerResponse<String> validResponse = checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PERFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效或过期");
        }
        if (StringUtils.equals(token, forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, UserDO userDO, String passwordNew) {
        // 查询用户名与旧密码之间的关系，防止横向越权（即如果只查询passwordOld在数据库中是否存在，会导致通过多次密码提交尝试来使resultCount>0）
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), userDO.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        userDO.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(userDO);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码修改成功");
        }
        return ServerResponse.createByErrorMessage("密码修改失败");
    }

    @Override
    public ServerResponse<UserDO> updateUserInformation(UserDO userDO) {
        int resultCount = userMapper.checkEmailByUserId(userDO.getEmail(), userDO.getId());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("邮箱已被注册");
        }
        UserDO updateUserDO = new UserDO.UserDOBuilder().buildId(userDO.getId()).buildEmail(userDO.getEmail()).buildPhone(userDO.getPhone())
                .buildQuestion(userDO.getQuestion()).buildAnwser(userDO.getAnswer()).build();
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUserDO);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功", updateUserDO);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<UserDO> getUserInformation(Integer id) {
        UserDO userDO = userMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        userDO.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(userDO);
    }

    /**
     * 校验该用户是否为管理员
     * @param user
     * @return
     */
    @Override
    public ServerResponse checkAdminRole(UserDO user) {
        if (user != null && user.getRole() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}