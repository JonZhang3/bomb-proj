package com.bombproj.vo

import java.util.Date

import com.fasterxml.jackson.annotation.JsonFormat

import scala.beans.BeanProperty

case class ProjectMemberListVO() {

    @BeanProperty var id: String = _
    @BeanProperty var userId: String = _
    @BeanProperty var permission: String = _
    @BeanProperty var userName: String = _
    @BeanProperty var nickName: String = _
    @BeanProperty var email: String = _
    @BeanProperty var phone: String = _
    @BeanProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") var createTime: Date = _

}
