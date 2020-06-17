package com.bombproj.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Integer page;
    private String userId;

}
