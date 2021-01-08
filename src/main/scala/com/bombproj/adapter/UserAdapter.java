package com.bombproj.adapter;

import com.bombproj.constants.Constants;
import com.bombproj.constants.LoginType;
import com.bombproj.constants.UserState;
import com.bombproj.constants.UserType;
import com.bombproj.dto.LoginDto;
import com.bombproj.model.User;
import com.bombproj.utils.MD5;
import com.bombproj.utils.Utils;
import com.queryflow.key.KeyGenerateUtil;

import java.util.Date;

public final class UserAdapter {

    private UserAdapter() {
    }

    public static User getModel(LoginDto loginDto) {
        Date now = new Date();
        User user = new User();
        String salt = Utils.getChars(Constants.PASSWORD_SALT_LENGTH());
        user.setId(KeyGenerateUtil.generateId() + "");
        user.setUserName(loginDto.getUserName());
        user.setPassword(MD5.encrytMD5(loginDto.getPassword(), salt));
        user.setNickName(loginDto.getNickName());
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setState(UserState.VALID.getCode());
        user.setType(UserType.NORMAL.getCode());
        user.setEmail(loginDto.getEmail());
        user.setPhone(loginDto.getPhone());
        user.setPasswordSalt(salt);
        user.setAuth("");
        user.setAuthName("");
        user.setRoleId("");
        user.setRoleName("");
        user.setAvatarUrl("");
        user.setLoginType(LoginType.COMMON.getCode());
        user.setSequence(0L);
        return user;
    }

}
