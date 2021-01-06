package com.bombproj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5 {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5.class);

    /**
     * MD5 + 盐，防止数据库被盗，第三方使用碰撞法解密用户账号（彩虹碰撞）
     *
     * @param data         要加密的数据
     * @param passwordSalt 加密盐
     * @return 加密后的字符串
     */
    public static String encrytMD5(String data, String passwordSalt) {
        if (data == null) {
            return "";
        }
        if (passwordSalt == null) {
            passwordSalt = "";
        }
        passwordSalt = passwordSalt.trim();
        data = passwordSalt + data.trim();
        try {
            //指定加密算法
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(data.getBytes());
            return encryptMD5toString(digest.digest());
        } catch (Exception e) {
            LOGGER.error("MD5加密异常", e);
            return null;
        }
    }

    //将加密后的字节数组转化为固定长度的字符串
    private static String encryptMD5toString(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        try {
            StringBuilder str = new StringBuilder();
            String str16;
            for (int i = 0; i < data.length; i++) {
                //转换为16进制数据
                //Integer.toHexString的参数是int，如果不进行&0xff，那么当一个byte会转换成int时，由于int是32位，而byte只有8位这时会进行补位，
                //例如补码11111111的十进制数为-1转换为int时变为11111111111111111111111111111111好多1啊，呵呵！即0xffffffff但是这个数是不对的，这种补位就会造成误差。
                //和0xff相与后，高24比特就会被清0了，结果就对了。
                str16 = Integer.toHexString(0xFF & data[i]);
                if (str16.length() == 1) {
                    str.append("0").append(str16);
                } else {
                    str.append(str16);
                }
            }
            return str.toString();
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }
}
