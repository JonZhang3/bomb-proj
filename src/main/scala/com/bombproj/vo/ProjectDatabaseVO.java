package com.bombproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectDatabaseVO {

    private Integer id;
    private String databaseName;
    private String databaseDesc;
    private String dbHost;
    private String dbPort;
    private String userName;
    private String version;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    private Date createTime;
    private String createUser;

}
