package com.bombproj.dto

import javax.validation.constraints.{Max, Min, NotBlank}
import org.hibernate.validator.constraints.Length

import scala.beans.BeanProperty

@SerialVersionUID(1L)
case class ServerDto() {

    @BeanProperty var id: String = _

    @NotBlank(message = "主机名称不能为空", groups = Array(classOf[NewOrUpdateServer]))
    @Length(max = 64, message = "主机名称最大长度为 64", groups = Array(classOf[NewOrUpdateServer]))
    @BeanProperty var serverName: String = _

    @Length(max = 64, message = "描述信息最大长度为 64", groups = Array(classOf[NewOrUpdateServer]))
    @BeanProperty var description: String = _

    @BeanProperty var serverGroup: String = _

    @NotBlank(message = "HostName 不能为空", groups = Array(classOf[NewOrUpdateServer]))
    @BeanProperty var hostName: String = _

    @Max(value = 65535, message= "主机SSH端口号应在 1 ~ 65535 之间", groups = Array(classOf[NewOrUpdateServer]))
    @Min(value = 1, message = "主机SSH端口号应在 1 ~ 65535 之间", groups = Array(classOf[NewOrUpdateServer]))
    @BeanProperty var sshPort: Int = _

    @NotBlank(message = "主机用户名不能为空", groups = Array(classOf[NewOrUpdateServer]))
    @Length(max = 64, message = "主机用户名最大长度为 64", groups = Array(classOf[NewOrUpdateServer]))
    @BeanProperty var username: String = _

    @NotBlank(message = "主机SSH密码不能为空", groups = Array(classOf[NewOrUpdateServer]))
    @BeanProperty var pass: String = _

    @BeanProperty var rememberPass: String = _

    @BeanProperty var tags: String = _

    @BeanProperty var userId: String = _

    @BeanProperty var page: Int = _
    @BeanProperty var name: String = _ //server or host name, for query

    trait NewOrUpdateServer

}
