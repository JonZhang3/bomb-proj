package com.bombproj.framework;

import com.bombproj.utils.Aes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;

public final class WebUtils {

    private WebUtils() {
    }

    private static final int DEFAULT_COKKIE_MAX_AGE = 60 * 60 * 24 * 7;

    public static HttpServletRequest request() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static HttpServletResponse response() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    public static HttpSession session() {
        return request().getSession();
    }

    public static Object getSessionAttribute(String name) {
        return request().getSession().getAttribute(name);
    }

    public static void setSessionAttribute(String name, Object value) {
        request().getSession().setAttribute(name, value);
    }

    public static void removeSessionAttribute(String name) {
        request().getSession().removeAttribute(name);
    }

    public static void addCookie(Cookie cookie) {
        if (cookie != null) {
            response().addCookie(cookie);
        }
    }

    public static void addCookie(String key, String value) {
        addCookie(key, value, false, DEFAULT_COKKIE_MAX_AGE);
    }

    public static void addCookie(String key, String value, int maxAge) {
        addCookie(key, value, false, maxAge);
    }

    public static void addCookie(String key, String value, boolean encryption) {
        addCookie(key, value, encryption, DEFAULT_COKKIE_MAX_AGE);
    }

    public static void addCookie(String key, String value, boolean encryption, int maxAge) {
        if (Utils.isEmpty(key) || Utils.isEmpty(value)) {
            return;
        }
        String val = value;
        if (encryption) {
            val = Aes.encrypt(val);
        } else {
            val = Base64.getEncoder().encodeToString(val.getBytes());
        }
        Cookie cookie = new Cookie(key, val);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response().addCookie(cookie);
    }

    public static void deleteCookie(String key) {
        Cookie[] cookies = request().getCookies();
        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(key)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response().addCookie(cookie);
                }
            }
        }
    }

}
