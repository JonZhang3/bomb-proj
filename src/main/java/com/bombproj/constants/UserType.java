package com.bombproj.constants;

public enum UserType {

    NORMAL((byte) 1, "普通用户"),
    ADMIN((byte) 100, "管理员");

    private final byte code;
    private final String name;

    UserType(byte code, String name) {
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
        for (UserType value : UserType.values()) {
            if (value.getCode() == type) {
                return value.getName();
            }
        }
        return "";
    }

}
