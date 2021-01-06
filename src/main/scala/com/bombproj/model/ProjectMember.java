package com.bombproj.model;

import com.queryflow.annotation.Column;
import com.queryflow.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("project_member")
public class ProjectMember {

    private String id;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private String projectId;
    private String userId;
    private String permission;

    @Column(exists = false)
    private String userEmail;
    @Column(exists = false)
    private String userPhone;
    @Column(exists = false)
    private String userName;
    @Column(exists = false)
    private String userNickName;

}
