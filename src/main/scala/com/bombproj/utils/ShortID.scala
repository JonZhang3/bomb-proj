package com.bombproj.utils

import java.security.SecureRandom
import java.util.Random

case class ShortID private(private val random: Random,
                      private val alphabet: String,
                      private val reduceTime: Long,
                      private val version: Int,
                      private val clusterWorkerId: Int) {
    @volatile
    private var counter = 0
    @volatile
    private var previousSeconds = 0L
    private val shuffled = this.shuffle(alphabet)

    def generate(): String = {
        var str = ""
        val seconds = Math.floor((System.currentTimeMillis - reduceTime) * 0.001).toLong
        if (seconds == previousSeconds) counter += 1
        else {
            counter = 0
            previousSeconds = seconds
        }
        str = str + encode(version)
        str = str + encode(clusterWorkerId)
        if (counter > 0) str = str + encode(counter)
        str = str + encode(seconds.toInt)
        str
    }

    private def encode(number: Int): String = {
        var loopCounter = 0
        var done = false
        val str = new StringBuilder
        var index = 0
        while (!done) {
            index = ((number >> (4 * loopCounter)) & 0x0f) | randomByte
            str.append(shuffled(index))
            done = number < Math.pow(16, loopCounter + 1)
            loopCounter += 1
        }
        str.toString
    }

    private def randomByte: Int = {
        val bytes = new Array[Byte](1)
        random.nextBytes(bytes)
        bytes(0) & 0x30
    }

    private def shuffle(alphabet: String): Array[Char] = {
        val source = new StringBuilder(alphabet)
        val target = new StringBuilder

        var r: Double = 0
        var charIndex: Int = 0

        while (source.nonEmpty) {
            r = random.nextDouble()
            charIndex = Math.floor(r * source.length).toInt
            target.append(source charAt charIndex)
            source deleteCharAt charIndex
        }
        target.toString().toCharArray
    }

}

object ShortID {
    private val DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZab"

    private val DEFAULT_REDUCE_TIME = 1403265799803L
    private val DEFAULT_VERSION = 6

    private val INSTANCE = new ShortID(new SecureRandom, DEFAULT_ALPHABET, DEFAULT_REDUCE_TIME,
        DEFAULT_VERSION, 0)

    def randomID: String = {
        INSTANCE generate()
    }
}
