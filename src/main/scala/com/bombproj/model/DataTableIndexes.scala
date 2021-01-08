package com.bombproj.model

import com.queryflow.annotation.Table

import scala.beans.BeanProperty

@Table("datatable_indexes")
case class DataTableIndexes() {

    @BeanProperty var id: Long = _
    @BeanProperty var `type`: String = _
    @BeanProperty var method: String = _
    @BeanProperty var sort: String = _
    @BeanProperty var dbType: String = _
    @BeanProperty var sequence: Int = _

}
