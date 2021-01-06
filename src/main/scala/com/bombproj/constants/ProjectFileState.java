package com.bombproj.constants;

public enum ProjectFileState {

    DELETED(0, "彻底删除"),
    NORMAL(1, "正常"),
    RECYCLED(2, "回收站");

    private final int code;
    private final String name;

    ProjectFileState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getState() {
        return code;
    }

    public String getName() {
        return name;
    }

}
