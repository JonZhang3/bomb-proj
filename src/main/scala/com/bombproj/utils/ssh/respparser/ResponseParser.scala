package com.bombproj.utils.ssh.respparser

import java.io.InputStream

trait ResponseParser[T] {

    def parse(messages: (String, String)): T

    def parseStream(inputs: (InputStream, InputStream)): T

}
