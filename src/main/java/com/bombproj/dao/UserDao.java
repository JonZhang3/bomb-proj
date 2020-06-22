package com.bombproj.dao;

import com.bombproj.constants.UserState;
import com.bombproj.model.User;
import com.bombproj.vo.UserQueryResultVO;
import com.queryflow.accessor.A;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    public List<User> queryUserByNameOrEmailOrPhone(String userNameOrEmailOrPhone) {
        String sql = "SELECT * FROM users WHERE state = ? AND userName = ?" +
            "UNION SELECT * FROM users WHERE state = ? AND email = ?" +
            "UNION SELECT * FROM users WHERE state = ? AND phone = ?";
        return A.query(sql, UserState.VALID.getCode(), userNameOrEmailOrPhone,
            UserState.VALID.getCode(), userNameOrEmailOrPhone,
            UserState.VALID.getCode(), userNameOrEmailOrPhone).list(User.class);
    }

    public Integer queryUserCountByUsername(String userName) {
        String sql = "SELECT COUNT(id) FROM users WHERE state = ? AND userName = ?";
        return A.query(sql, UserState.VALID.getCode(), userName).one(Integer.class);
    }

    public Integer queryUserCountByEmail(String email) {
        String sql = "SELECT COUNT(id) FROM users WHERE state = ? AND email = ?";
        return A.query(sql, UserState.VALID.getCode(), email).one(Integer.class);
    }

    public Integer queryUserCountByPhone(String phone) {
        String sql = "SELECT COUNT(id) FROM users WHERE state = ? AND phone = ?";
        return A.query(sql, UserState.VALID.getCode(), phone).one(Integer.class);
    }

}
