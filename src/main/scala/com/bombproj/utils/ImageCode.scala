package com.bombproj.utils

import java.awt.image.BufferedImage
import java.awt.{Color, Font, GraphicsEnvironment}
import java.io.{FileOutputStream, IOException, OutputStream}
import java.util.Random

import com.bombproj.utils.ImageCode.DEFAULT_FONT
import javax.imageio.ImageIO

import scala.util.control.Breaks.break

class ImageCode(width: Int = 80,// 图片的宽度。
                height: Int = 30,// 图片的高度。
                codeCount: Int = 4,// 验证码字符个数
                lineCount: Int = 20,// 验证码干扰线数
                font: String = DEFAULT_FONT) {

    // 验证码
    private var code: String = _
    // 验证码图片Buffer
    private var buffImg: BufferedImage = _

    private val codeSequence = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    def createCode(fontStr: String): Unit = { // 检查系统是否支持指定的字体
        var _fontStr = fontStr
        try {
            var supportFont = false
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
            val fontFamilies = ge.getAvailableFontFamilyNames
            for (s <- fontFamilies) {
                if (s == _fontStr) {
                    supportFont = false
                    break
                }
            }
            if (!supportFont) _fontStr = fontFamilies(0)
        } catch {
            case e: Exception =>
                e.printStackTrace()
        }
        var x = 0
        var codeY = 0
        var red = 0
        var green = 0
        var blue = 0
        x = width / (codeCount + 2) // 每个字符的宽度

        codeY = height - 4
        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g = buffImg.getGraphics
        // 生成随机数
        val random = new Random
        // 将图像填充为白色
        val color = new Color(255, 255, 255)
        g.setColor(color)
        g.fillRect(0, 0, width, height)
        // 创建字体
        val font = new Font(_fontStr, Font.PLAIN, 26)
        g.setFont(font)
        for (i <- 0 until lineCount) {
            val xs = random.nextInt(width)
            val ys = random.nextInt(height)
            val xe = xs + random.nextInt(width / 8)
            val ye = ys + random.nextInt(height / 8)
            red = random.nextInt(255)
            green = random.nextInt(255)
            blue = random.nextInt(255)
            g.setColor(new Color(red, green, blue))
            g.drawLine(xs, ys, xe, ye)
        }
        // randomCode记录随机产生的验证码
        val randomCode = new StringBuffer
        // 随机产生codeCount个字符的验证码。
        for (i <- 0 until codeCount) {
            val strRand = String.valueOf(codeSequence(random.nextInt(codeSequence.length)))
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255)
            green = random.nextInt(255)
            blue = random.nextInt(255)
            g.setColor(new Color(red, green, blue))
            g.drawString(strRand, (i + 1) * x, codeY)
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand)
        }
        // 将四位数字的验证码保存到Session中。
        code = randomCode.toString
    }

    @throws[IOException]
    def write(path: String): Unit = {
        val sos = new FileOutputStream(path)
        this.write(sos)
    }

    @throws[IOException]
    def write(sos: OutputStream): Unit = {
        ImageIO.write(buffImg, "png", sos)
        sos.close()
    }

    def getBuffImg: BufferedImage = buffImg

    def getCode: String = code

}

object ImageCode {
    private val DEFAULT_FONT = "Times New Roman"
}
