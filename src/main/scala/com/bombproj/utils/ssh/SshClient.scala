package com.bombproj.utils.ssh

import java.io.{BufferedReader, File, FileInputStream, FileNotFoundException, IOException, InputStream, InputStreamReader, OutputStream}

import com.bombproj.utils.Utils
import com.bombproj.utils.ssh.SshClient.{COMMAND_PREFIX, DEFAULT_TIMEOUT}
import com.jcraft.jsch.{ChannelExec, JSch, Session}

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

    def exec(command: String): (String, String) = {
        if(Utils.isEmpty(command)) return ("", "")
        var channel: ChannelExec = null
        var input: InputStream = null
        val sb = new StringBuilder
        val errResponse = new StringBuilder
        try {
            channel = session.openChannel("exec").asInstanceOf[ChannelExec]
            channel.setCommand(COMMAND_PREFIX + command)
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
        (sb.toString(), errResponse.toString())
    }

    def execLines(): Unit = {

    }

    def execStream(command: String): (InputStream, InputStream) = {
        if(Utils.isEmpty(command)) return (null, null)
        var channel: ChannelExec = null
        try {
            channel = session.openChannel("exec").asInstanceOf[ChannelExec]
            channel.setCommand(COMMAND_PREFIX + command)
            val input = channel.getInputStream
            val errInput = channel.getErrStream
            channel.connect(DEFAULT_TIMEOUT)
            return (input, errInput)
        } finally {
            if(channel != null) channel.disconnect()
        }
        (null, null)
    }

    def shell(): Unit = {

    }

    def scpTo(source: String, dest: String = ""): Int = {
        if(Utils.isEmpty(source)) throw new IOException("source not be null")
        val sourceFile = new File(source)
        if(!sourceFile.exists()) throw new FileNotFoundException("source file not found")
        var _dest = dest
        if(Utils.isEmpty(_dest)) _dest = "."
        var channel: ChannelExec = null
        var output: OutputStream = null
        try {
            channel = session.openChannel("exec").asInstanceOf[ChannelExec]
            _dest = _dest.replace("'", "'\"'\"'")
            _dest = "'" + _dest + "'"
            var command = "scp"
            command += " -t " + _dest
            channel.setCommand(command)
            output = channel.getOutputStream
            val input = channel.getInputStream
            channel.connect(DEFAULT_TIMEOUT)
            if(checkAck(input) != 0) return -1
            val fileSize = sourceFile.length()
            command = "C0644 " + fileSize + " "
            if(source.lastIndexOf('/') > 0) command += source.substring(source.lastIndexOf('/') + 1)
            else command += source
            command += "\n"
            output.write(command.getBytes())
            output.flush()
            if(checkAck(input) != 0) return -1
            var fis = new FileInputStream(sourceFile)
            val buffer = new Array[Byte](1024)
            var len: Int = fis.read(buffer, 0, buffer.length)
            while (len > 0) {
                output.write(buffer, 0, len)
                len = fis.read(buffer, 0, buffer.length)
            }
            fis.close(); fis = null
            output.write('\0')
            output.flush()
            if(checkAck(input) != 0) {
                return -1
            }
        } finally {
            if(output != null) output.close()
            if(channel != null) channel.disconnect()
        }
        0
    }

    private def checkAck(in: InputStream): Int = {
        val b = in.read()
        if (b == 1 || b == 2) {
            val sb = new StringBuilder
            var c: Int = 0
            do {
                c = in.read()
                sb.append(c.toChar)
            } while (c != '\n')
        }
        b
    }

}

object SshClient {

    private val DEFAULT_TIMEOUT: Int = 15 * 1000
    private val COMMAND_PREFIX = "source /etc/profile;"

    def main(args: Array[String]): Unit = {
        val client = new SshClient("192.168.33.16", "root", "1qaz@WSX")
        client.connect()
        println(client.exec("java -verbose"))
//        val flag = client.scpTo("/Users/jon/Desktop/a.jpeg", "/home")
//        println(flag)
        client.disconnect() // 2462
    }

}
