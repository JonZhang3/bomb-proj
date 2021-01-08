package com.bombproj.dto

import scala.beans.BeanProperty

@SerialVersionUID(1L)
case class ProjectMemberDto() {

    @BeanProperty var userName: String = _ // 成员姓名
    @BeanProperty var page: Int = _
    @BeanProperty var projectId: String = _
    @BeanProperty var createUserId: String = _

}
