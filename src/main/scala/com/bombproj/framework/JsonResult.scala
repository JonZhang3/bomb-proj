package com.bombproj.framework

import scala.collection.mutable

@SerialVersionUID(1L)
class JsonResult private () extends mutable.HashMap[String, Any] with Serializable {

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

    def success: JsonResult = success(MESSAGE_SUCCESS)

    def success(data: Any): JsonResult = success(MESSAGE_SUCCESS, data)

    def success(message: String): JsonResult = new JsonResult().code(CODE_SUCCESS).message(message)

    def success(message: String, data: Any): JsonResult = new JsonResult().code(CODE_SUCCESS).message(message).data(data)

    def error: JsonResult = error(MESSAGE_ERROR)

    def error(data: Any): JsonResult = error(MESSAGE_ERROR, data)

    def error(message: String): JsonResult = new JsonResult().code(CODE_ERROR).message(message)

    def error(message: String, data: Any): JsonResult = new JsonResult().code(CODE_ERROR).message(message).data(data)

}
