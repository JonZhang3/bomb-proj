package com.bombproj.vo;

import lombok.Data;

@Data
public class DataTableIndexesVO {

    private String id;
    private String indexName;
    private String[] fieldNames;
    private String indexType;
    private String indexMethod;
    private Integer state;
    private String version;
    private String createUserName;

}
