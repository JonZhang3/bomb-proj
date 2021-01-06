package com.bombproj.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectMemberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;// 成员姓名

    private Integer page;

    private String projectId;
    private String createUserId;

}
