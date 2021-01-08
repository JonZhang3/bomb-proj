package com.bombproj.dto

import javax.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Length

import scala.beans.BeanProperty

case class ProjectDataTableDto() {

    @BeanProperty var projectId: String = _
    @BeanProperty var userId: String = _
    @BeanProperty var tableId: String = _
    @BeanProperty var databaseId: Long = _

    @BeanProperty
    @NotEmpty(message = "表名不能为空")
    @Length(max = 50, message = "表名最大长度为 50")
    @BeanProperty var tableName: String = _

    @BeanProperty
    @Length(max = 255, message = "描述最大长度为 255")
    var tableDesc: String = _

    @BeanProperty var fields: String = _
    @BeanProperty var indexes: String = _

}
