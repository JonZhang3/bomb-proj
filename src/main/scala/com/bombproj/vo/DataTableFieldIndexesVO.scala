package com.bombproj.vo

import java.util
import java.util.List

import scala.beans.BeanProperty

case class DataTableFieldIndexesVO() {

    @BeanProperty var value: String = _
    @BeanProperty var label: String = _
    @BeanProperty var children: util.List[DataTableFieldIndexesVO] = _

}
