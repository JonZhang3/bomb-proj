package com.bombproj.utils

import java.security.MessageDigest

import org.slf4j.LoggerFactory

object MD5 {

    private val LOGGER = LoggerFactory.getLogger(MD5.getClass)

    def encrytMD5(data: String, passwordSalt: String = ""): String = {
        var src = data
        if (src == null) return ""
        val ps = passwordSalt.trim
        src = ps + src
        try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(src.getBytes("UTF-8"))
            encryptMD5toString(digest.digest())
        } catch {
            case e: Exception => {
                LOGGER.error("", e)
                null
            }
        }
    }

    private def encryptMD5toString(data: Array[Byte]): String = {
        if (data == null || data.length == 0) ""
        try {
            val str = new StringBuilder
            var str16: String = null
            for (item <- data) {
                //toHexString的参数是int，如果不进行&0xff，那么当一个byte会转换成int时，由于int是32位，而byte只有8位这时会进行补位，
                //例如补码11111111的十进制数为-1转换为int时变为11111111111111111111111111111111好多1啊，呵呵！即0xffffffff但是这个数是不对的，这种补位就会造成误差。
                //和0xff相与后，高24比特就会被清0了，结果就对了。
                str16 = (0xFF & item).toHexString
                if (str16.length == 1) str.append("0")
                str.append(str16)
            }
            str.toString
        } catch {
            case e: Exception =>
                LOGGER.error("", e)
                null
        }
    }

}
