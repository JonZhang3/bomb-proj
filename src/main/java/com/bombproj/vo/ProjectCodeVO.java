package com.bombproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectCodeVO {

    private String id;
    private String code;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

}
