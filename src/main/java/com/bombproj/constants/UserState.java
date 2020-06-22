package com.bombproj.constants;

public enum UserState {

    VALID(1, "有效"),
    INVALID(2, "邮箱未验证"),
    FORBID(0, "无效");

    private final int code;
    private final String name;

    UserState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByValue(byte type) {
        for (UserState value : UserState.values()) {
            if (value.getCode() == type) {
                return value.getName();
            }
        }
        return "";
    }

}
