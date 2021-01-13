package com.bombproj.vo

import java.util.Date

import com.fasterxml.jackson.annotation.JsonFormat

import scala.beans.BeanProperty

@SerialVersionUID(1L)
case class ServerVO() {

    @BeanProperty var id: String = _
    @BeanProperty var serverName: String = _
    @BeanProperty var hostName: String = _
    @BeanProperty var sshPort: String = _
    @BeanProperty var describe: String = _
    @BeanProperty var username: String = _
    @BeanProperty var tags: String = _
    @BeanProperty var createUserName: String = _

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @BeanProperty var createTime: Date = _

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @BeanProperty var updateTime: Date = _

}
