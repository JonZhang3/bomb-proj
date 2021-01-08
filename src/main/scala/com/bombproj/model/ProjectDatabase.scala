package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.{Column, Table}

import scala.beans.BeanProperty

@Table("project_database")
case class ProjectDatabase() {

    @BeanProperty
    @Column(exists = false) var id: Long = _

    @BeanProperty var databaseName: String = _
    @BeanProperty var databaseDesc: String = _
    @BeanProperty var `type`: String = _
    @BeanProperty var dbHost: String = _
    @BeanProperty var dbPort: String = _
    @BeanProperty var userName: String = _
    @BeanProperty var testDbHost: String = _
    @BeanProperty var testDbPort: String = _
    @BeanProperty var testUserName: String = _
    @BeanProperty var prodDbHost: String = _
    @BeanProperty var prodDbPort: String = _
    @BeanProperty var prodUserName: String = _
    @BeanProperty var version: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var projectId: String = _
    @BeanProperty var userId: String = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _

}
