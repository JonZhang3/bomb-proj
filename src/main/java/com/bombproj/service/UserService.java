package com.bombproj.service;

import com.bombproj.adapter.UserAdapter;
import com.bombproj.dao.UserDao;
import com.bombproj.dto.LoginDto;
import com.bombproj.framework.BusinessException;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.WebUtils;
import com.bombproj.model.User;
import com.bombproj.utils.MD5;
import com.bombproj.utils.Utils;
import com.bombproj.vo.UserQueryResultVO;
import com.queryflow.sql.SqlBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User login(LoginDto model) {
        if (Utils.isEmpty(model.getVerificationCode())) {
            throw new BusinessException("验证码有误");
        }
        if (!model.getVerificationCode().equals(WebUtils.getSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE))) {
            throw new BusinessException("验证码有误");
        }
        List<User> users = this.userDao.queryUserByNameOrEmailOrPhone(model.getUserName());
        if (users != null && users.size() == 1) {
            User user = users.get(0);
            if (Utils.isNotEmpty(user.getPassword())
                && MD5.encrytMD5(model.getPassword(), user.getPasswordSalt()).equals(user.getPassword())) {
                return user;
            }
            throw new BusinessException("用户名或密码错误");
        }
        throw new BusinessException("用户名或密码错误");
    }

    public void register(LoginDto model) {
        if (!model.getPassword().equals(model.getRpassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }
        if (!model.getVerificationCode().equals(WebUtils.getSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE))) {
            throw new BusinessException("验证码有误");
        }
        if (!Utils.checkEmail(model.getEmail())) {
            throw new BusinessException("邮箱格式错误");
        }
        if (!Utils.checkPhone(model.getPhone())) {
            throw new BusinessException("手机号格式错误");
        }
        int count = this.userDao.queryUserCountByUsername(model.getUserName());
        if (count > 0) {
            throw new BusinessException("该用户名已存在");
        }
        count = this.userDao.queryUserCountByEmail(model.getEmail());
        if (count > 0) {
            throw new BusinessException("该邮箱已被注册");
        }
        count = this.userDao.queryUserCountByPhone(model.getPhone());
        if (count > 0) {
            throw new BusinessException("该手机号已被注册");
        }
        User user = UserAdapter.getModel(model);
        SqlBox.insert(user);
    }

}
