package com.bombproj.model

import java.util.Date

import com.fasterxml.jackson.annotation.JsonFormat
import com.queryflow.annotation.Table
import com.queryflow.annotation.Column

import scala.beans.BeanProperty

@SerialVersionUID(1L)
@Table("project")
case class Project() {

    @BeanProperty var id: String = _
    @BeanProperty var uniKey: String = _
    @BeanProperty var projectName: String = _
    @BeanProperty var projectDesc: String = _
    @BeanProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") var createTime: Date = _
    @BeanProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") var updateTime: Date = _
    @BeanProperty var state: Int = _
    @BeanProperty var sequence: Long = _
    @BeanProperty var remark: String = _
    @BeanProperty var userId: String = _

    @BeanProperty
    @Column(exists = false) var userName: String = _

    @BeanProperty var `type`: Int = _
    @BeanProperty var pass: String = _
    @BeanProperty var cover: String = _
    @BeanProperty var luceneSearch: String = _
    @BeanProperty var gitAddr: String = _

}
