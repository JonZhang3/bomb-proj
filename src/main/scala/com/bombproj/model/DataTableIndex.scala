package com.bombproj.model

import java.util.Date

import scala.beans.BeanProperty

case class DataTableIndex() {

    @BeanProperty var id: Long = _
    @BeanProperty var indexName: String = _
    @BeanProperty var fieldNames: String = _
    @BeanProperty var indexType: String = _
    @BeanProperty var indexMethod: String = _
    @BeanProperty var projectId: String = _
    @BeanProperty var databaseId: Long = _
    @BeanProperty var datatableId: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var version: String = _
    @BeanProperty var userId: String = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _
    @BeanProperty var sequence: Int = _

}
