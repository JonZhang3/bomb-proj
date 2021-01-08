package com.bombproj.framework.exception

class BusinessException(val code: Int = -1,
                        val message: String,
                        val cause: Throwable = null) extends RuntimeException(message, cause, false, false) {

}
