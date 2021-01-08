package com.bombproj.dto

import scala.beans.BeanProperty

@SerialVersionUID(1L)
case class ProjectFileDto() {

    @BeanProperty var name: String = _
    @BeanProperty var sortName: String = _
    @BeanProperty var sortValue: String = _
    @BeanProperty var projectId: String = _
    @BeanProperty var folderId: String = _
    @BeanProperty var creatUserId: String = _
    @BeanProperty var page: Int = _
    @BeanProperty var pageSize: Int = _

}
