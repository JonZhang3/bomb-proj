package com.bombproj.vo

import java.util.Date

import com.fasterxml.jackson.annotation.JsonFormat

import scala.beans.BeanProperty

case class ProjectDataTableVO() {

    @BeanProperty var id: String = _
    @BeanProperty var tableName: String = _
    @BeanProperty var tableDesc: String = _
    @BeanProperty var `type`: String = _
    @BeanProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") var updateTime: Date = _
    @BeanProperty var createUser: String = _

}
