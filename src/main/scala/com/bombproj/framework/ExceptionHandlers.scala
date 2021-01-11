package com.bombproj.framework

import com.bombproj.framework.ExceptionHandlers.LOGGER
import com.bombproj.framework.exception.{BusinessException, NotFoundException}
import org.slf4j.LoggerFactory
import org.springframework.validation.{BindException, BindingResult}
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler(Array(classOf[BindException], classOf[MethodArgumentNotValidException]))
    @ResponseBody
    def handleE(e: Exception): JsonResult = {
        var bindResult: BindingResult = null
        e match {
            case exception: BindException => bindResult = exception.getBindingResult
            case exception: MethodArgumentNotValidException => bindResult = exception.getBindingResult
            case _ =>
        }
        var errMsg = "参数错误"
        if(bindResult != null && bindResult.hasErrors) {
            errMsg =bindResult.getAllErrors.get(0).getDefaultMessage
            if(errMsg.contains("NumberFormatException")) errMsg = "参数类型错误"
        }
        JsonResult.error(message = errMsg)
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
