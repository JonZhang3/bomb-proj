package com.bombproj.vo

import java.util.Date

import com.fasterxml.jackson.annotation.JsonFormat

import scala.beans.BeanProperty

case class ProjectDatabaseVO() {

    @BeanProperty var id: Int = _
    @BeanProperty var databaseName: String = _
    @BeanProperty var databaseDesc: String = _
    @BeanProperty var dbHost: String = _
    @BeanProperty var dbPort: String = _
    @BeanProperty var userName: String = _
    @BeanProperty var version: String = _
    @BeanProperty var `type`: String = _
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") private var updateTime: Date = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var createUser: String = _

}
