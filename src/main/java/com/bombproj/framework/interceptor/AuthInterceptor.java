package com.bombproj.framework.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if(!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                return true;
            }
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            if(authPassport == null || !authPassport.validate()) {
                return true;
            }

        } catch (Exception e) {

        } finally {

        }
        return super.preHandle(request, response, handler);
    }
}
