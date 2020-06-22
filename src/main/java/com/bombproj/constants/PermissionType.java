package com.bombproj.constants;

public enum PermissionType {

    PROJECT(1, "项目权限");

    private final int code;
    private final String name;

    PermissionType(int code, String name) {
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
