package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.Table

import scala.beans.BeanProperty

@Table("project_datatable")
case class ProjectDataTable() {

    @BeanProperty var id: String = _
    @BeanProperty var tableName: String = _
    @BeanProperty var tableDesc: String = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _
    @BeanProperty var state: Int = _
    @BeanProperty var projectId: String = _
    @BeanProperty var userId: String = _
    @BeanProperty var databaseId: Long = _

}
