package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.Table

import scala.beans.BeanProperty

@Table("project_file")
case class ProjectFile() {

    @BeanProperty var id: String = _
    @BeanProperty var fileName: String = _
    @BeanProperty var suffix: String = _
    @BeanProperty var size: Long = _
    @BeanProperty var parentId: String = _
    @BeanProperty var creator: String = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _
    @BeanProperty var `type`: Int = _
    @BeanProperty var state: Int = _
    @BeanProperty var starred: Int = _
    @BeanProperty var shared: Int = _
    @BeanProperty var sharedPassword: String = _
    @BeanProperty var sharedId: String = _
    @BeanProperty var version: Long = _
    @BeanProperty var projectId: String = _
    @BeanProperty var recycledBy: String = _
    @BeanProperty var deletedBy: String = _

}
