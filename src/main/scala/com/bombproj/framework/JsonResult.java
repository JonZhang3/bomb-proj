package com.bombproj.framework;

import java.io.Serializable;
import java.util.HashMap;

public class JsonResult extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -1L;

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_ERROR = -1;

    private static final String MESSAGE_SUCCESS = "操作成功";
    private static final String MESSAGE_ERROR = "操作失败";

    public static JsonResult success() {
        return success(MESSAGE_SUCCESS);
    }

    public static JsonResult success(Object data) {
        return success(MESSAGE_SUCCESS, data);
    }

    public static JsonResult success(String message) {
        return new JsonResult()
            .code(CODE_SUCCESS)
            .message(message);
    }

    public static JsonResult success(String message, Object data) {
        return new JsonResult()
            .code(CODE_SUCCESS)
            .message(message)
            .data(data);
    }

    public static JsonResult error() {
        return error(MESSAGE_ERROR);
    }

    public static JsonResult error(Object data) {
        return error(MESSAGE_ERROR, data);
    }

    public static JsonResult error(String message) {
        return new JsonResult()
            .code(CODE_ERROR)
            .message(message);
    }

    public static JsonResult error(String message, Object data) {
        return new JsonResult()
            .code(CODE_ERROR)
            .message(message)
            .data(data);
    }

    public static JsonResult build() {
        return new JsonResult();
    }

    private JsonResult() {

    }

    public JsonResult code(int code) {
        put("code", code);
        return this;
    }

    public JsonResult message(String message) {
        put("message", message);
        return this;
    }

    public JsonResult data(Object data) {
        put("data", data);
        return this;
    }

}
