package com.bombproj.dto

import javax.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

import scala.beans.BeanProperty

@SerialVersionUID(1L)
case class ServerGroupDto() {

    @BeanProperty var id: String = _

    @NotBlank(message = "主机组名称不能为空", groups = Array(classOf[NewOrUpdateGroup]))
    @Length(max = 64, message = "主机组名称最大长度为 64", groups = Array(classOf[NewOrUpdateGroup]))
    @BeanProperty var groupName: String = _

    @Length(max = 64, message = "主机组简介最大长度为 64", groups = Array(classOf[NewOrUpdateGroup]))
    @BeanProperty var groupDesc: String = _

    @BeanProperty var userId: String = _

    @BeanProperty var page: Int = _

    trait NewOrUpdateGroup

}
