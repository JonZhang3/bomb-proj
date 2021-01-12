package com.bombproj.utils.ssh.respparser

import java.io.InputStream

trait ResponseParser[T] {

    def parse(message: String): T

    def parse(input: InputStream): T

}
