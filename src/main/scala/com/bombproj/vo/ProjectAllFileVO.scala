package com.bombproj.vo

import scala.beans.BeanProperty

case class ProjectAllFileVO() {

    @BeanProperty var id: String = _
    @BeanProperty var fileName: String = _
    @BeanProperty var suffix: String = _
    @BeanProperty var size: Long = _
    @BeanProperty var creator: String = _
    @BeanProperty var creatorName: String = _
    @BeanProperty var updateTime: String = _
    @BeanProperty var `type`: Int = _
    @BeanProperty var starred: Int = _
    @BeanProperty var shared: Int = _
    @BeanProperty var version: String = _
    @BeanProperty var parentId: String = _
    @BeanProperty var fullPath: String = _

}
