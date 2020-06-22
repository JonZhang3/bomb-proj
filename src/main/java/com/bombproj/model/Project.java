package com.bombproj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.queryflow.annotation.Column;
import com.queryflow.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Table("project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String uniKey;
    private String projectName;
    private String projectDesc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    private Integer state;
    private Long sequence;
    private String remark;
    private String userId;

    @Column(exists = false)
    private String userName;

    private Integer type;
    private String pass;
    private String cover;
    private Integer luceneSearch;
    private String gitAddr;

}
