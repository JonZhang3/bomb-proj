package com.bombproj.dto

import javax.validation.constraints.{Max, Min, NotEmpty, NotNull}
import org.hibernate.validator.constraints.Length

import scala.beans.BeanProperty

case class ProjectDatabaseDto() {

    @BeanProperty var id: String = _
    @BeanProperty var projectId: String = _
    @BeanProperty var userId: String = _

    @BeanProperty
    @NotEmpty(message = "数据库名不能为空")
    @Length(max = 50, message = "数据库名最大长度为 50")
    var databaseName: String = _

    @BeanProperty
    @Length(max = 255, message = "描述最大长度为 255")
    var databaseDesc: String = _

    @BeanProperty
    @NotEmpty(message = "数据库类型不能为空")
    var `type`: String = _

    @BeanProperty
    @NotEmpty(message = "数据库主机名不能为空")
    var host: String = _

    @BeanProperty
    @NotNull(message = "数据库端口号不能为空")
    @Max(value = 65535, message = "端口号必须在 1~65535 之间")
    @Min(value = 1, message = "端口号必须在 1~65535 之间")
    var port: Int = _

    @BeanProperty
    @Length(max = 50, message = "数据库版本最大长度为 50")
    var version: String = _

    @BeanProperty var userName: String = _
    @BeanProperty var password: String = _

}
