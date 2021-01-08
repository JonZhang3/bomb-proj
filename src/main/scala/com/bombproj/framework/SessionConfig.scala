package com.bombproj.framework

import com.bombproj.constants.UserType

@SerialVersionUID(1L)
class SessionConfig extends Serializable {

    var userId: String = _
    var userName: String = _
    var nickName: String = _
    var userType: UserType = _

}

object SessionConfig {

    val KEY_VERIFICATION_CODE = "__ver_code"
    val KEY_SESSION_CONFIG = "__session_config"

    def current(): SessionConfig = {
        WebUtils.getSessionAttribute(KEY_SESSION_CONFIG).asInstanceOf[SessionConfig]
    }

}
