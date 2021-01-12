package com.bombproj.utils.ssh.respparser

import java.io.InputStream

class JavaCommandResponseParser extends ResponseParser[Map[String, Any]] {

    override def parse(messages: (String, String)): Map[String, Any] = {

        null
    }

    override def parseStream(inputs: (InputStream, InputStream)): Map[String, Any] = {
        null
    }

    def parseLines(lines: List[String]): Map[String, Any] = {

    }

}
