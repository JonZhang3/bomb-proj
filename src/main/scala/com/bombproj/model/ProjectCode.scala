package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.Table

import scala.beans.BeanProperty

@Table("project_code")
case class ProjectCode() {

    @BeanProperty var id: String = _
    @BeanProperty var code: String = _
    @BeanProperty var message: String = _
    @BeanProperty var projectId: String = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _
    @BeanProperty var state: Int = _

}
