package com.bombproj.dto

import javax.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

import scala.beans.BeanProperty

case class ProjectCodeDto() {

    @BeanProperty var id: String = _

    @BeanProperty var projectId: String = _

    @BeanProperty
    @NotBlank(message = "状态码不能为空", groups = Array(classOf[AddOrUpdateProjectCodeGroup]))
    @Length(max = 50, message = "状态码最大长度为 50", groups = Array(classOf[AddOrUpdateProjectCodeGroup]))
    var code: String = _

    @BeanProperty
    @NotBlank(message = "状态码描述不能为空", groups = Array(classOf[AddOrUpdateProjectCodeGroup]))
    @Length(max = 128, message = "状态码描述最大长度为 128", groups = Array(classOf[AddOrUpdateProjectCodeGroup]))
    var message: String = _

    @BeanProperty var page: Int = _

    @BeanProperty var queryText: String = _

    trait AddOrUpdateProjectCodeGroup

}
