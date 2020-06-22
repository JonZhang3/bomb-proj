package com.bombproj.constants;

public enum ProjectMemberState {

    NORMAL(1, "正常");

    private final int code;
    private final String name;

    ProjectMemberState(int code, String name) {
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
