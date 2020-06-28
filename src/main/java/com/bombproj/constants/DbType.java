package com.bombproj.constants;

public enum DbType {

    MYSQL("mysql"),
    ORACLE("oracle");

    private final String name;

    DbType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean isOneOf(String dbType) {
        for (DbType type : values()) {
            if(type.name.equals(dbType)) {
                return true;
            }
        }
        return false;
    }

}
