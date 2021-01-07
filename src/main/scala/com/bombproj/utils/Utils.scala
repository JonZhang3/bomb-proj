package com.bombproj.utils

import java.lang.reflect.Type
import java.util.Random
import java.util.regex.Pattern

import com.google.gson.Gson

object Utils {

    private val CHARS = "123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ789abcd"
    private val PATTERN_EMAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
    private val PATTERN_PHONE = Pattern.compile("1[3456789]\\d{9}")

    val GSON = new Gson

    def fromJson[T](json: String, `type`: Type): T = GSON.fromJson(json, `type`)

    def toJson(src: Any): String = GSON.toJson(src)

    def isEmpty(src: String): Boolean = src == null || src.isEmpty

    def isNotEmpty(src: String): Boolean = !isEmpty(src)

    def getChars(length: Int): String = {
        val size = CHARS.length
        val random = new Random
        val result = new StringBuilder(length)
        for (_ <- 0 until length) {
            result.append(CHARS.charAt(random.nextInt(size)))
        }
        result.toString
    }

    def checkEmail(email: String): Boolean = {
        if (isEmpty(email)) return false
        PATTERN_EMAIL.matcher(email).matches
    }

    def checkPhone(phone: String): Boolean = {
        if (isEmpty(phone)) return false
        PATTERN_PHONE.matcher(phone).matches
    }

    def getFileSuffix(fileName: String): String = {
        if (isEmpty(fileName)) return ""
        fileName.substring(fileName.lastIndexOf(".") + 1)
    }

}
