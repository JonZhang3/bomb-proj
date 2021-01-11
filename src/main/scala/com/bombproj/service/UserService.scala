package com.bombproj.service

import java.util
import java.util.List

import com.bombproj.adapter.UserAdapter
import com.bombproj.dao.UserDao
import com.bombproj.dto.LoginDto
import com.bombproj.framework.{SessionConfig, WebUtils}
import com.bombproj.framework.exception.BusinessException
import com.bombproj.model.User
import com.bombproj.utils.{MD5, Utils}
import com.queryflow.sql.SqlBox
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class UserService @Resource()(userDao: UserDao) {

    def login(model: LoginDto): User = {
        if (Utils.isEmpty(model.getVerificationCode)) throw new BusinessException(message = "验证码有误")
        if (!(model.getVerificationCode == WebUtils.getSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE)))
            throw new BusinessException(message = "验证码有误")
        val users: util.List[User] = this.userDao.queryUserByNameOrEmailOrPhone(model.getUserName)
        if (users != null && users.size == 1) {
            val user: User = users.get(0)
            if (Utils.isNotEmpty(user.getPassword) && MD5.encrytMD5(model.getPassword, user.getPasswordSalt) == user.getPassword) return user
            throw new BusinessException(message = "用户名或密码错误")
        }
        throw new BusinessException(message = "用户名或密码错误")
    }

    def register(model: LoginDto): Unit = {
        if (!(model.getPassword == model.getRpassword)) throw new BusinessException(message = "两次输入的密码不一致")
        if (!(model.getVerificationCode == WebUtils.getSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE)))
            throw new BusinessException(message = "验证码有误")
        if (!Utils.checkEmail(model.getEmail)) throw new BusinessException(message = "邮箱格式错误")
        if (!Utils.checkPhone(model.getPhone)) throw new BusinessException(message = "手机号格式错误")
        var count: Int = this.userDao.queryUserCountByUsername(model.getUserName)
        if (count > 0) throw new BusinessException(message = "该用户名已存在")
        count = this.userDao.queryUserCountByEmail(model.getEmail)
        if (count > 0) throw new BusinessException(message = "该邮箱已被注册")
        count = this.userDao.queryUserCountByPhone(model.getPhone)
        if (count > 0) throw new BusinessException(message = "该手机号已被注册")
        val user: User = UserAdapter.getModel(model)
        SqlBox.insert(user)
    }

}
