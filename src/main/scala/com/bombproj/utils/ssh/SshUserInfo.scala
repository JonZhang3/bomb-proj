package com.bombproj.utils.ssh

import com.jcraft.jsch.UserInfo

class SshUserInfo (val password: String) extends UserInfo {

    override def getPassphrase: String = null

    override def getPassword: String = password

    override def promptPassword(message: String): Boolean = true

    override def promptPassphrase(message: String): Boolean = false

    override def promptYesNo(message: String): Boolean = true

    override def showMessage(message: String): Unit = {}
}
