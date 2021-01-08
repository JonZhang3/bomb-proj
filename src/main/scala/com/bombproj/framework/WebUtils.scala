package com.bombproj.framework

import java.util.Base64

import com.bombproj.utils.{Aes, Utils}
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse, HttpSession}
import org.springframework.web.context.request.{RequestContextHolder, ServletRequestAttributes}

object WebUtils {

    private val DEFAULT_COKKIE_MAX_AGE = 60 * 60 * 24 * 7

    def request: HttpServletRequest =
        RequestContextHolder.currentRequestAttributes.asInstanceOf[ServletRequestAttributes].getRequest

    def response: HttpServletResponse =
        RequestContextHolder.currentRequestAttributes.asInstanceOf[ServletRequestAttributes].getResponse

    def session: HttpSession = request.getSession

    def getSessionAttribute(name: String): Any = request.getSession.getAttribute(name)

    def setSessionAttribute(name: String, value: Any): Unit = {
        request.getSession.setAttribute(name, value)
    }

    def removeSessionAttribute(name: String): Unit = {
        request.getSession.removeAttribute(name)
    }

    def addCookie(cookie: Cookie): Unit = {
        if (cookie != null) response.addCookie(cookie)
    }

    def addCookie(key: String, value: String,
                  encryption: Boolean = false,
                  maxAge: Int = DEFAULT_COKKIE_MAX_AGE): Unit = {
        if (Utils.isEmpty(key) || Utils.isEmpty(value)) return
        var _val = value
        if (encryption) _val = Aes.encrypt(_val)
        else _val = Base64.getEncoder.encodeToString(_val.getBytes)
        val cookie = new Cookie(key, _val)
        cookie.setMaxAge(maxAge)
        cookie.setPath("/")
        response.addCookie(cookie)
    }

    def deleteCookie(key: String): Unit = {
        val cookies = request.getCookies
        if (cookies != null && cookies.nonEmpty)
            for (cookie <- cookies) {
                if (cookie.getName == key) {
                    cookie.setValue(null)
                    cookie.setMaxAge(0)
                    cookie.setPath("/")
                    response.addCookie(cookie)
                }
            }
    }

}
