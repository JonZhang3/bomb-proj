package com.bombproj.utils.ssh

import com.jcraft.jsch.JSch

class SshClient(val host: String, val username: String, val password: String) {

    private var jsch: JSch = null

    def connect(): Unit = {
        jsch = new JSch

    }

}
