package com.bombproj.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public JsonResult handleBusinessException(BusinessException e) {
        return JsonResult.build()
            .code(e.getCode())
            .message(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult handleException(Exception e) {
        LOGGER.error("", e);
        return JsonResult.error("系统异常");
    }

}
