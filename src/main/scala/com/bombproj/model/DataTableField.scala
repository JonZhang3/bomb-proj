package com.bombproj.model

import java.util.{Date, Objects}

import com.queryflow.annotation.{Column, Table}

import scala.beans.BeanProperty

@Table("datatable_field")
case class DataTableField() {

    @BeanProperty var id: String = _
    @BeanProperty var fieldName: String = _
    @BeanProperty var `type`: String = _
    @BeanProperty var length: String = _
    @BeanProperty var notNull: String = _
    @BeanProperty var pk: String = _
    @BeanProperty var autoIncrement: String = _
    @BeanProperty var defaultValue: String = _
    @Column("_unsigned")
    @BeanProperty var unsigned: String = _
    @Column("_zerofill")
    @BeanProperty var zerofill: String = _
    @Column("_charset")
    @BeanProperty var charset: String = _
    @Column("_collation")
    @BeanProperty var collation: String = _
    @Column("_binary")
    @BeanProperty var binary: String = _
    @BeanProperty var onUpdateCT: String = _
    @Column("_decimal")
    @BeanProperty var decimal: String = _
    @BeanProperty var valueList: String = _
    @BeanProperty var notes: String = _
    @BeanProperty var indexes: String = _
    @BeanProperty var indexesName: String = _
    @BeanProperty var state: Int = _
    @BeanProperty var marker: String = _
    @BeanProperty var version: String = _
    @BeanProperty var datatableId: String = _
    @BeanProperty var projectId: String = _
    @BeanProperty var databaseId: Long = _
    @BeanProperty var userId: String = _
    @BeanProperty var createTime: Date = _
    @BeanProperty var updateTime: Date = _
    @BeanProperty var sequence: String = _

    override def equals(o: Any): Boolean = {
        if(this == o) return true
        if (o == null || (getClass != o.getClass)) return false
        val that = o.asInstanceOf[DataTableField]
        Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(`type`, that.`type`) &&
            Objects.equals(length, that.length) &&
            Objects.equals(notNull, that.notNull) &&
            Objects.equals(defaultValue, that.defaultValue) &&
            Objects.equals(indexes, that.indexes) &&
            Objects.equals(indexesName, that.indexesName)
    }

    override def hashCode: Int =
        Objects.hash(id, fieldName, `type`, length, notNull, defaultValue, notes, indexes, indexesName,
            state.asInstanceOf[Integer], marker,
            version, datatableId, projectId, userId, createTime, updateTime)

}
