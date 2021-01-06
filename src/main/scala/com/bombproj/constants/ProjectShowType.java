package com.bombproj.constants;

public enum ProjectShowType {

    ALL(0, "所有项目"),
    MY_CREATE(1, "我创建的项目"),
    JOIN(2, "我加入的项目"),
    CREATE_JOIN(3, "创建或加入的项目");

    private final int code;
    private final String name;

    ProjectShowType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getType() {
        return code;
    }

    public String getStringType() {
        return code + "";
    }

    public String getName() {
        return name;
    }

}
