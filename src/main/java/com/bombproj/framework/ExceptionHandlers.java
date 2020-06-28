package com.bombproj.framework;

import com.bombproj.framework.exception.BusinessException;
import com.bombproj.framework.exception.NotFoundException;
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

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(NotFoundException e) {
        throw e;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult handleException(Exception e) {
        LOGGER.error("系统异常", e);
        return JsonResult.error("系统异常");
    }

}
