package com.bombproj.model;

import com.queryflow.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("project_file")
public class ProjectFile {

    private String id;
    private String fileName;
    private String suffix;
    private Long size;
    private String parentId;
    private String creator;
    private Date createTime;
    private Date updateTime;
    private Integer type;
    private Integer state;
    private Integer starred;
    private Integer shared;
    private String sharedPassword;
    private String sharedId;
    private Long version;
    private String projectId;
    private String recycledBy;
    private String deletedBy;

}
