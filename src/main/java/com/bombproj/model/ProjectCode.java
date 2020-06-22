package com.bombproj.model;

import com.queryflow.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("project_code")
public class ProjectCode {

    private String id;
    private String code;
    private String message;
    private String projectId;
    private Date createTime;
    private Date updateTime;
    private Integer state;

}
