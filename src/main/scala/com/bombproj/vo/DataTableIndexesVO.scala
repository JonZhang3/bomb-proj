package com.bombproj.vo

import scala.beans.BeanProperty

case class DataTableIndexesVO() {

    @BeanProperty var id: String = _
    @BeanProperty var indexName: String = _
    @BeanProperty var fieldNames: Array[String] = _
    @BeanProperty var indexType: String = _
    @BeanProperty var indexMethod: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var version: String = _
    @BeanProperty var createUserName: String = _

}
