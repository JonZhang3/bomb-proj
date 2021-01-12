package com.bombproj.utils.ssh

import java.io.{BufferedReader, InputStream, InputStreamReader}

import ch.ethz.ssh2.{Connection, SCPClient}
import com.bombproj.utils.Utils
import com.bombproj.utils.ssh.SshClient.DEFAULT_TIMEOUT
import com.jcraft.jsch.{ChannelExec, JSch, Session}

import scala.io.Source

class SshClient(val host: String, val username: String, val password: String, val port: Int = 22) {

    private var jsch: JSch = _
    private var session: Session = _

    def connect(connectTimeout: Int): Boolean = {
        jsch = new JSch
        session = jsch.getSession(username, host, port)
        session.setUserInfo(new SshUserInfo(password))
        // ConnectException 连接被拒绝
        session.connect(connectTimeout)
        session.isConnected
    }

    def connect(): Boolean = connect(DEFAULT_TIMEOUT)

    def disconnect(): Unit = {
        session.disconnect()
    }

    def exec(command: String): String = {
        if(Utils.isEmpty(command)) return ""
        var channel: ChannelExec = null
        var input: InputStream = null
        val sb = new StringBuilder
        try {
            channel = session.openChannel("exec").asInstanceOf[ChannelExec]
            channel.setCommand(command)
            input = channel.getInputStream
            channel.connect(DEFAULT_TIMEOUT)
            val reader = new BufferedReader(new InputStreamReader(input, "UTF-8"))
            var line: String = reader.readLine()
            while (line != null) {
                sb.append(line + "\n")
                line = reader.readLine()
            }
        } finally {
            if(input != null) input.close()
            if(channel != null) channel.disconnect()
        }
        sb.toString()
    }

}

object SshClient {

    private val DEFAULT_TIMEOUT: Int = 15 * 1000

    def main(args: Array[String]): Unit = {
//        val client = new SshClient("81.68.133.247", "root", "1qaz@WSX")
//        client.connect()
//        println("exec ls")
//        val start = System.currentTimeMillis()
//        val str = client.exec("ls /")
//        println(System.currentTimeMillis() - start)
//        client.disconnect() // 2462
        test2()
    }

    def test2(): Unit = {
        val conn = new Connection("81.68.133.247")
        conn.connect()
        val result = conn.authenticateWithPassword("root", "1qaz@WSX")
        println("connection " + result)
        val session = conn.openSession()
        val sb = new StringBuilder
        val start = System.currentTimeMillis()
        session.execCommand("ls /;echo 1")
        val input: InputStream = session.getStdout
        val reader = new BufferedReader(new InputStreamReader(input, "UTF-8"))
        var line: String = reader.readLine()
        while (line != null) {
            sb.append(line + "\n")
            line = reader.readLine()
        }
        println(System.currentTimeMillis() - start)
        println(sb.toString())
        SCPClient
        conn.close()
    }

}
