package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.{Column, Table}

import scala.beans.BeanProperty

@SerialVersionUID(1L)
@Table("project_member")
case class ProjectMember() {

    @BeanProperty var id: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _
    @BeanProperty var projectId: String = _
    @BeanProperty var userId: String = _
    @BeanProperty var permission: String = _

    @BeanProperty
    @Column(exists = false) var userEmail: String = _
    @BeanProperty
    @Column(exists = false) var userPhone: String = _
    @BeanProperty
    @Column(exists = false) var userName: String = _
    @BeanProperty
    @Column(exists = false) var userNickName: String = _

}
