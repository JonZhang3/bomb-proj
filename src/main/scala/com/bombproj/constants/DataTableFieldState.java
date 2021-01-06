package com.bombproj.constants;

public enum DataTableFieldState {

    DELETED(0, "删除"),
    COMMON(1, "正常");

    private final int code;
    private final String name;

    DataTableFieldState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getState() {
        return this.code;
    }

    public String getName() {
        return name;
    }
}
