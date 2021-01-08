package com.bombproj.framework

import com.bombproj.framework.ExceptionHandlers.LOGGER
import com.bombproj.framework.exception.{BusinessException, NotFoundException}
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseBody}

@ControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(Array(classOf[BusinessException]))
    @ResponseBody
    def handleBusinessException(e: BusinessException): JsonResult = {
        JsonResult()
            .code(e.code)
            .message(e.getMessage)
    }

    @ExceptionHandler(Array(classOf[NotFoundException]))
    def handleNotFoundException(e: NotFoundException): Unit = {
        throw e
    }

    @ExceptionHandler(Array(classOf[Exception]))
    @ResponseBody def handleException(e: Exception): JsonResult = {
        LOGGER.error("系统异常", e)
        JsonResult.error("系统异常")
    }

}

object ExceptionHandlers {
    private val LOGGER = LoggerFactory.getLogger(ExceptionHandlers.getClass)
}
