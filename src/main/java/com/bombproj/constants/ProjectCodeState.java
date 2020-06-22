package com.bombproj.constants;

public enum ProjectCodeState {

    DELETED(0, "删除"),
    COMMON(1, "正常");

    private final int code;
    private final String name;

    ProjectCodeState(int code, String name) {
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
