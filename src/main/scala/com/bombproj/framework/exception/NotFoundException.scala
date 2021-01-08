package com.bombproj.framework.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException (val message: String = "",
                         val cause: Throwable = null) extends RuntimeException(message, cause, false, false){

}
