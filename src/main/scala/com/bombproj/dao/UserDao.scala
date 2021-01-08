package com.bombproj.dao

import java.util

import com.bombproj.constants.UserState
import com.bombproj.model.User
import com.queryflow.accessor.A
import org.springframework.stereotype.Repository

@Repository
class UserDao {

    def queryUserByNameOrEmailOrPhone(userNameOrEmailOrPhone: String): util.List[User] = {
        val sql = "SELECT * FROM users WHERE state = ? AND userName = ?" +
            "UNION SELECT * FROM users WHERE state = ? AND email = ?" +
            "UNION SELECT * FROM users WHERE state = ? AND phone = ?"
        A.query(sql, UserState.VALID.getCode, userNameOrEmailOrPhone,
            UserState.VALID.getCode, userNameOrEmailOrPhone,
            UserState.VALID.getCode, userNameOrEmailOrPhone)
            .list(classOf[User])
    }

    def queryUserCountByUsername(userName: String): Integer = {
        val sql = "SELECT COUNT(id) FROM users WHERE state = ? AND userName = ?"
        A.query(sql, UserState.VALID.getCode, userName).one(classOf[Integer])
    }

    def queryUserCountByEmail(email: String): Integer = {
        val sql = "SELECT COUNT(id) FROM users WHERE state = ? AND email = ?"
        A.query(sql, UserState.VALID.getCode, email).one(classOf[Integer])
    }

    def queryUserCountByPhone(phone: String): Integer = {
        val sql = "SELECT COUNT(id) FROM users WHERE state = ? AND phone = ?"
        A.query(sql, UserState.VALID.getCode, phone).one(classOf[Integer])
    }

}
