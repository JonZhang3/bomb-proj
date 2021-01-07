package com.bombproj.constants;

public enum SortValue {

    ASC("asc"),
    DESC("desc");

    private final String name;

    SortValue(String name) {
        this.name = name;
    }

    public static SortValue ofValue(String value) {
        if(Utils.isEmpty(value)) {
            return ASC;
        }
        String lowerCaseValue = value.toLowerCase();
        if(ASC.name.equals(lowerCaseValue)) {
            return ASC;
        } else if(DESC.name.equals(lowerCaseValue)) {
            return DESC;
        }
        return ASC;
    }

}
