package com.bombproj.constants;

public enum  ProjectFileType {

    DIR(1, "目录"),
    FILE(2, "文件"),
    IMAGE(3, "图片");

    private final int code;
    private final String name;

    ProjectFileType(int code, String name) {
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
