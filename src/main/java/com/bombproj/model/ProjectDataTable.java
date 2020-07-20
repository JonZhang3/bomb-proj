package com.bombproj.model;

import com.queryflow.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("project_datatable")
public class ProjectDataTable {

    private String id;
    private String tableName;
    private String tableDesc;
    private Date createTime;
    private Date updateTime;
    private String type;
    private Integer state;
    private String projectId;
    private String userId;
    private Long databaseId;

}
