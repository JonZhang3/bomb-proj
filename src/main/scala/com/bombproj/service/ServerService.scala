package com.bombproj.service

import com.bombproj.dao.ServerDao
import com.bombproj.dto.ServerDto
import com.jcraft.jsch.{ChannelExec, JSch, UserInfo}
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class ServerService @Resource() (serverDao: ServerDao) {

    def newServer(dto: ServerDto): Unit = {

    }

}

object Main {
    def main(args: Array[String]): Unit = {
        val jsch = new JSch
        val session = jsch.getSession("root", "192.168.33.16")
//        session.setPassword("1qaz@WSX")
        val userInfo = new UserInfo {
            override def getPassphrase: String = null

            override def getPassword: String = "1qaz@WSX"

            override def promptPassword(s: String): Boolean = {
                println("promptPassword: " + s)
                true
            }

            override def promptPassphrase(s: String): Boolean = {
                println("promptPassphrase: " + s)
                false
            }

            override def promptYesNo(s: String): Boolean = {
                println("promptYesNo: " + s)
                true
            }

            override def showMessage(s: String): Unit = {
                println("showMessage: " + s)
            }
        }
        session.setUserInfo(userInfo)
        session.connect(30000)
        val channel = session.openChannel("exec")
        channel.asInstanceOf[ChannelExec].setCommand("ls /")
        channel.setInputStream(null)

        channel.asInstanceOf[ChannelExec].setErrStream(System.err)
        channel.setOutputStream(System.out)
        channel.connect()
    }

}
