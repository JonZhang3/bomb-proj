package com.bombproj.framework

import com.bombproj.constants.UserType

@SerialVersionUID(1L)
case class SessionConfig() {

    var userId: String = _
    var userName: String = _
    var nickName: String = _
    var userType: UserType = _

}

object SessionConfig {

    val KEY_VERIFICATION_CODE = "__ver_code"
    val KEY_SESSION_CONFIG = "__session_config"

    private val currentUser = new SessionConfig()
    currentUser.userId = "admin"
    currentUser.userName = "admin"
    currentUser.nickName = "admin"
    currentUser.userType = UserType.NORMAL

    def current(): SessionConfig = {
        currentUser
//        WebUtils.getSessionAttribute(KEY_SESSION_CONFIG).asInstanceOf[SessionConfig]
    }

}
