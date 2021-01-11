package com.bombproj.model

import java.util.Date

import com.queryflow.annotation.Table

import scala.beans.BeanProperty

@Table("servers")
case class Server() {

    @BeanProperty var id: String = _
    @BeanProperty var serverName: String = _
    @BeanProperty var hostName: String = _
    @BeanProperty var sshPort: Int = _
    @BeanProperty var describe: String = _
    @BeanProperty var username: String = _
    @BeanProperty var pass: String = _
    @BeanProperty var tags: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var creator: String = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _

}
