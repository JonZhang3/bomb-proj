package com.bombproj.framework.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 8638237486526577302L;

    private int code = -1;

    public BusinessException(int code, String message) {
        this(message, null);
        this.code = code;
    }

    public BusinessException(String message) {
        this(message, null);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause, false, false);
    }

    public int getCode() {
        return code;
    }
}
