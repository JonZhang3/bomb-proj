package com.bombproj.constants;

public enum ProjectType {

    PRIVATE(1, "私有项目"),
    PUBLIC(2, "公开项目");

    private final int code;
    private final String name;

    ProjectType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getType() {
        return code;
    }

    public String getName() {
        return name;
    }
}
