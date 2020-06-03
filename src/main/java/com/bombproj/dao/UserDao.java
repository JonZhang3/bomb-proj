package com.bombproj.dao;

import com.bombproj.constants.UserStatus;
import com.bombproj.model.User;
import com.queryflow.accessor.A;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    public List<User> queryUserByNameOrEmailOrPhone(String userNameOrEmailOrPhone) {
        String sql = "SELECT * FROM `user` WHERE `status` = ? AND userName = ?" +
            "UNION SELECT * FROM `user` WHERE `status` = ? AND email = ?" +
            "UNION SELECT * FROM `user` WHERE `status` = ? AND phone = ?";
        return A.query(sql, UserStatus.VALID.getCode(), userNameOrEmailOrPhone,
            UserStatus.VALID.getCode(), userNameOrEmailOrPhone,
            UserStatus.VALID.getCode(), userNameOrEmailOrPhone).list(User.class);
    }

    public Integer queryUserCountByUsername(String userName) {
        String sql = "SELECT COUNT(id) FROM `user` WHERE `status` = ? AND userName = ?";
        return A.query(sql, UserStatus.VALID.getCode(), userName).one(Integer.class);
    }

    public Integer queryUserCountByEmail(String email) {
        String sql = "SELECT COUNT(id) FROM `user` WHERE `status` = ? AND email = ?";
        return A.query(sql, UserStatus.VALID.getCode(), email).one(Integer.class);
    }

    public Integer queryUserCountByPhone(String phone) {
        String sql = "SELECT COUNT(id) FROM `user` WHERE `status` = ? AND phone = ?";
        return A.query(sql, UserStatus.VALID.getCode(), phone).one(Integer.class);
    }

}
