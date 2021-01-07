package com.bombproj.utils

import com.bombproj.config.AppProperties
import com.bombproj.framework.SpringContextHolder
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import org.slf4j.LoggerFactory

object Aes {

    private val LOGGER = LoggerFactory.getLogger(Aes.getClass)

    val iv = "CRAPG_@W8#_19#10"

    private def handlerKey(apiKey: String): String = {
        var _apiKey = apiKey
        val tempKey = "HDALd)9dkA*&1kS$CKSJ}{|A"
        if (_apiKey.length > 16) _apiKey = _apiKey.substring(0, 16)
        else if (_apiKey.length < 16) _apiKey = _apiKey + tempKey.substring(0, 16 - apiKey.length)
        _apiKey
    }

    def encrypt(data: String): String = {
        val appProperties = SpringContextHolder.getBean(classOf[AppProperties])
        val PWD = handlerKey(appProperties.getAesKey)
        try {
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val blockSize = cipher.getBlockSize
            val dataBytes = data.getBytes
            var plaintextLength = dataBytes.length
            if (plaintextLength % blockSize != 0) plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize))
            val plaintext = new Array[Byte](plaintextLength)
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length)
            val keyspec = new SecretKeySpec(PWD.getBytes, "AES")
            val ivspec = new IvParameterSpec(iv.getBytes)
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)
            val encrypted = cipher.doFinal(plaintext)
            parseByte2HexStr(encrypted)
        } catch {
            case e: Exception =>
                LOGGER.error("", e)
                null
        }
    }

    def desEncrypt(data: String): String = {
        var _data = data
        val appProperties = SpringContextHolder.getBean(classOf[AppProperties])
        val PWD = handlerKey(appProperties.getAesKey)
        try {
            if (_data == null || _data == "") return ""
            _data = _data.trim
            val encrypted1 = parseHexStr2Byte(_data)
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val keyspec = new SecretKeySpec(PWD.getBytes, "AES")
            val ivspec = new IvParameterSpec(iv.getBytes)
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec)
            val original = cipher.doFinal(encrypted1)
            val originalString = new String(original)
            originalString.trim
        } catch {
            case e: Exception =>
                e.printStackTrace()
                null
        }
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    def parseByte2HexStr(buf: Array[Byte]): String = {
        val sb = new StringBuffer
        for (i <- buf.indices) {
            var hex = Integer.toHexString(buf(i) & 0xFF)
            if (hex.length == 1) hex = '0' + hex
            sb.append(hex.toUpperCase)
        }
        sb.toString
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 16进制字符串
     * @return
     */
    def parseHexStr2Byte(hexStr: String): Array[Byte] = {
        if (hexStr.length < 1) return null
        val result = new Array[Byte](hexStr.length / 2)
        for (i <- 0 until hexStr.length / 2) {
            val high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16)
            val low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16)
            result(i) = (high * 16 + low).toByte
        }
        result
    }

}
