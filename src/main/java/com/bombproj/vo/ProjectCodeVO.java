package com.bombproj.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectCodeVO {

    private String id;
    private String code;
    private String message;
    private Date createTime;

}
