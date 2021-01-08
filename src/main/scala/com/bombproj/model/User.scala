package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.Table

import scala.beans.BeanProperty

@SerialVersionUID(1L)
@Table("users")
case class User() {

    @BeanProperty var id: String = _ // ID
    @BeanProperty var userName: String = _ // 用户名
    @BeanProperty var password: String = _ // 密码
    @BeanProperty var nickName: String = _ // 昵称/真实姓名
    @BeanProperty var roleId: String = _ // 角色ID
    @BeanProperty var roleName: String = _ // 角色名
    @BeanProperty var auth: String = _
    @BeanProperty var authName: String = _
    @BeanProperty var createTime: Date = _ // 创建时间
    @BeanProperty var updateTime: Date = _ // 更新时间
    @BeanProperty var state: Int = _ // 状态 1- 有效
    @BeanProperty var sequence: Long = _ // 排序，越大越靠前
    @BeanProperty var `type`: Byte = _ // 用户类型：1普通用户，100：管理员
    @BeanProperty var email: String = _ // 邮箱
    @BeanProperty var phone: String = _ // 手机号
    @BeanProperty var avatarUrl: String = _ // 头像
    @BeanProperty var loginType: Int = _ // 0：账号登陆，1：github登陆，2：码云
    @BeanProperty var thirdlyId: String = _ // 第三方唯一ID
    @BeanProperty var passwordSalt: String = _ // 密码MD5盐


}
