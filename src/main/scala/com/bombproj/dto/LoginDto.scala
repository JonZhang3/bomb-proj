package com.bombproj.dto

import javax.validation.constraints.{Email, NotBlank}
import org.hibernate.validator.constraints.Length

import scala.beans.BeanProperty

@SerialVersionUID(1L)
case class LoginDto() {

    @BeanProperty var id: String = _

    @BeanProperty
    @NotBlank(message = "用户名不能为空") var userName: String = _

    @BeanProperty
    @NotBlank(message = "昵称不能为空", groups = Array(classOf[Register]))
    var nickName: String = _

    @BeanProperty
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能少于 6 位", groups = Array(classOf[Register]))
    var password: String = _

    @BeanProperty var rpassword: String = _

    @BeanProperty var remberPwd: String = _

    @BeanProperty
    @NotBlank(message = "验证码有误")
    var verificationCode: String = _

    @BeanProperty var sessionAdminName: String = _ // 如果sessionAdminName 不为null，则表示已经登录


    @BeanProperty var tipMessage: String = _

    @BeanProperty
    @Email(message = "邮箱格式错误", groups = Array(classOf[Register]))
    @NotBlank(message = "邮箱不能为空", groups = Array(classOf[Register]))
    var email: String = _

    @BeanProperty
    @NotBlank(message = "手机号不能为空", groups = Array(classOf[Register]))
    var phone: String = _

    @BeanProperty var attributes: String = _

    trait Register

}
