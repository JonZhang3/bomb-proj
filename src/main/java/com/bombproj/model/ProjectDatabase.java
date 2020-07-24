package com.bombproj.model;

import com.queryflow.annotation.Column;
import com.queryflow.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("project_database")
public class ProjectDatabase {

    @Column(exists = false)
    private Long id;

    private String databaseName;
    private String databaseDesc;
    private String type;
    private String dbHost;
    private String dbPort;
    private String userName;
    private Integer state;
    private String projectId;
    private String userId;
    private Date createTime;
    private Date updateTime;

}
