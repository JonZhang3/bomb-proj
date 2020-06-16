package com.bombproj.constants;

public enum LoginType {

    COMMON(0, "普通登录"),
    GITHUB(1, "GitHub"),
    OSCHINA(2, "OsChina");;

    private final int code;
    private final String name;

    LoginType(int code, String name) {
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
        for (LoginType value : LoginType.values()) {
            if (value.getCode() == type) {
                return value.getName();
            }
        }
        return "";
    }

}
