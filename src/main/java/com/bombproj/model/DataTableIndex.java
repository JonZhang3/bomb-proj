package com.bombproj.model;

import lombok.Data;

import java.util.Date;

@Data
public class DataTableIndex {

    private Long id;
    private String indexName;
    private String fieldNames;
    private String indexType;
    private String indexMethod;
    private String projectId;
    private Long databaseId;
    private String datatableId;
    private Integer state;
    private String version;
    private String userId;
    private Date createTime;
    private Date updateTime;
    private Integer sequence;

}
