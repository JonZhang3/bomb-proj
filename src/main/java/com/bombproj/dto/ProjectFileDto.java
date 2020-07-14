package com.bombproj.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectFileDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String sortName;
    private String sortValue;
    private String projectId;
    private String folderId;

    private String creatUserId;

    private Integer page;
    private Integer pageSize;

}
