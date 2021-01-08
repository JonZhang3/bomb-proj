package com.bombproj.vo

import scala.beans.BeanProperty

case class DataTableFieldVO() {

    @BeanProperty var name: String = _
    @BeanProperty var id: String = _
    @BeanProperty var fieldName: String = _
    @BeanProperty var `type`: String = _
    @BeanProperty var length: String = _
    @BeanProperty var notNull: String = _
    @BeanProperty var pk: String = _
    @BeanProperty var autoIncrement: String = _
    @BeanProperty var defaultValue: String = _
    @BeanProperty var _unsigned: String = _
    @BeanProperty var _zerofill: String = _
    @BeanProperty var _charset: String = _
    @BeanProperty var _collation: String = _
    @BeanProperty var _binary: String = _
    @BeanProperty var onUpdateCT: String = _
    @BeanProperty var _decimal: String = _
    @BeanProperty var valueList: Array[String] = _
    @BeanProperty var notes: String = _
    @BeanProperty var indexes: Array[String] = _
    @BeanProperty var indexesName: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var marker: String = _
    @BeanProperty var version: String = _
    @BeanProperty var createUserName: String = _

}
