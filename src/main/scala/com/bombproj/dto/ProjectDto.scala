package com.bombproj.dto

import javax.validation.constraints.NotBlank
import org.hibernate.validator.constraints.{Length, URL}

import scala.beans.BeanProperty

@SerialVersionUID(1L)
case class ProjectDto() {

    @BeanProperty var id: String = _

    @BeanProperty
    @NotBlank(message = "项目名不能为空", groups = Array(classOf[NewOrUpdateProjectGroup]))
    @Length(max = 50, message = "项目名最大长度为 50", groups = Array(classOf[NewOrUpdateProjectGroup]))
    var name: String = _

    @BeanProperty
    @Length(max = 255, message = "项目简介最大长度为 255", groups = Array(classOf[NewOrUpdateProjectGroup]))
    var desc: String = _

    @BeanProperty var `type`: Int = _

    @BeanProperty var page: Int = _

    @BeanProperty var cover: String = _

    @BeanProperty
    @URL.List(Array(
        new URL(protocol = "http"),
        new URL(protocol = "http")
    ))
    var gitAddr: String = _

    @BeanProperty var userId: String = _
    @BeanProperty var userName: String = _

    // query
    @BeanProperty var projectShowType: Int = _
    @BeanProperty var isAdmin: Boolean = _

    trait NewOrUpdateProjectGroup

}
