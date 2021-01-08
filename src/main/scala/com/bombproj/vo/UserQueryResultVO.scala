package com.bombproj.vo

import scala.beans.BeanProperty

case class UserQueryResultVO() {

    @BeanProperty var id: String = _
    @BeanProperty var userName: String = _
    @BeanProperty var nickName: String = _
    @BeanProperty var phone: String = _
    @BeanProperty var email: String = _

}
