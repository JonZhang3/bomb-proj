package com.bombproj.constants;

public enum UserStatus {

    VALID((byte) 1, "有效"),
    INVALID((byte) 2, "邮箱未验证"),
    FORBID((byte) 0, "无效");

    private final byte code;
    private final String name;

    UserStatus(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByValue(byte type) {
        for (UserStatus value : UserStatus.values()) {
            if (value.getCode() == type) {
                return value.getName();
            }
        }
        return "";
    }

}
