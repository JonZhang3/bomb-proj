package com.bombproj.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ProjectDatabaseDto {

    private String id;
    private String projectId;
    private String userId;

    @NotEmpty(message = "数据库名不能为空")
    @Length(max = 50, message = "数据库名最大长度为 50")
    private String databaseName;

    @Length(max = 255, message = "描述最大长度为 255")
    private String databaseDesc;

    @NotEmpty(message = "数据库类型不能为空")
    private String type;


}
