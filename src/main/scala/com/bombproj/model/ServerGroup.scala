package com.bombproj.model

import java.util.Date

import com.fasterxml.jackson.annotation.JsonFormat
import com.queryflow.annotation.{Column, Table}

import scala.beans.BeanProperty

@SerialVersionUID(1L)
@Table("server_group")
case class ServerGroup() {

    @BeanProperty var id: String = _
    @BeanProperty var groupName: String = _
    @BeanProperty var groupDesc: String = _
    @BeanProperty var creator: String = _
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @BeanProperty var createTime: Date = _
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @BeanProperty var updateTime: Date = _
    @BeanProperty var state: Int = _

    @Column(exists = false)
    @BeanProperty var userName: String = _

}
