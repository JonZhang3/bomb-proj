package com.bombproj.vo;

import lombok.Data;

@Data
public class DataTableFieldVO {

    private String name;
    private String fieldName;
    private String type;
    private String length;
    private Integer notNull;
    private String defaultValue;
    private String notes;
    private String indexes;
    private String indexesName;
    private Integer state;
    private String marker;
    private String version;
    private String createUserName;

}
