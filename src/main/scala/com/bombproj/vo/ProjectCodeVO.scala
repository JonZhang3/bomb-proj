package com.bombproj.vo

import java.util.Date

import com.fasterxml.jackson.annotation.JsonFormat

import scala.beans.BeanProperty

case class ProjectCodeVO() {

    @BeanProperty var id: String = _
    @BeanProperty var code: String = _
    @BeanProperty var message: String = _
    @BeanProperty @JsonFormat(pattern = "yyyy-MM-dd HH:mm") var createTime: Date = _

}
