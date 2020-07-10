package com.bombproj.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectFileDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sortName;
    private String sortValue;
    private Integer page;
    private Integer pageSize;

}
