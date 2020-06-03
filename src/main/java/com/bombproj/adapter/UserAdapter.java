package com.bombproj.adapter;

import com.bombproj.constants.Constants;
import com.bombproj.constants.UserStatus;
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
        String salt = Utils.getChars(Constants.PASSWORD_SALT_LENGTH);
        user.setId(KeyGenerateUtil.generateId() + "");
        user.setUserName(loginDto.getUserName());
        user.setPassword(MD5.encrytMD5(loginDto.getPassword(), salt));
        user.setNickName(loginDto.getNickName());
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setStatus(UserStatus.VALID.getCode());
        user.setType(UserType.NORMAL.getCode());
        user.setEmail(loginDto.getEmail());
        user.setPhone(loginDto.getPhone());
        user.setPasswordSalt(salt);
        return user;
    }

}
