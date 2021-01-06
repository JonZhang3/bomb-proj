package com.bombproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectDataTableVO {

    private String id;
    private String tableName;
    private String tableDesc;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    private String createUser;

}
