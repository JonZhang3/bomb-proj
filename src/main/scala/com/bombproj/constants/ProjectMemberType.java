package com.bombproj.constants;

public enum ProjectMemberType {

    CREATOR(1, "创建者"),
    MEMBER(2, "成员");

    private final int code;
    private final String name;

    ProjectMemberType(int code, String name) {
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
