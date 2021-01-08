package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.Table

import scala.beans.BeanProperty

@SerialVersionUID(1L)
@Table("log")
case class Log() {

    @BeanProperty var id: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var modelClass: String = _
    @BeanProperty var modelName: String = _
    @BeanProperty var `type`: String = _
    @BeanProperty var updateUserId: String = _
    @BeanProperty var updateUserName: String = _
    @BeanProperty var remark: String = _
    @BeanProperty var content: String = _

}
