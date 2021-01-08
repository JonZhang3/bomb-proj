package com.bombproj.model

import scala.beans.BeanProperty

case class Permission() {

    @BeanProperty var id: String = _
    @BeanProperty var name: String = _
    @BeanProperty var `type`: Int = _

}
