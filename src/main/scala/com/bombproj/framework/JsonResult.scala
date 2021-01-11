package com.bombproj.framework

import java.util

@SerialVersionUID(1L)
class JsonResult private () extends util.HashMap[String, Any] with Serializable {

    def code(code: Int): JsonResult = {
        put("code", code)
        this
    }

    def message(message: String): JsonResult = {
        put("message", message)
        this
    }

    def data(data: Any): JsonResult = {
        put("data", data)
        this
    }

}

object JsonResult {
    private val CODE_SUCCESS = 1
    private val CODE_ERROR = -1

    private val MESSAGE_SUCCESS = "操作成功"
    private val MESSAGE_ERROR = "操作失败"

    def apply(): JsonResult = new JsonResult()

    def success(message: String = MESSAGE_SUCCESS, data: Any = null): JsonResult = new JsonResult().code(CODE_SUCCESS).message(message).data(data)

    def error(message: String = MESSAGE_ERROR, data: Any = null): JsonResult = new JsonResult().code(CODE_ERROR).message(message).data(data)

}
