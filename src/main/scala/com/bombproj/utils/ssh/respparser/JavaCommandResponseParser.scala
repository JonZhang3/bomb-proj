package com.bombproj.utils.ssh.respparser

import java.io.InputStream

class JavaCommandResponseParser extends ResponseParser[Map[String, Any]] {

    override def parse(message: String): Map[String, Any] = {
        null
    }

    override def parse(input: InputStream): Map[String, Any] = {

    }
}
