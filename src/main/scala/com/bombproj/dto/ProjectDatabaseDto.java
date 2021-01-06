package com.bombproj.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotEmpty(message = "数据库主机名不能为空")
    private String host;

    @NotNull(message = "数据库端口号不能为空")
    @Max(value = 65535, message = "端口号必须在 1~65535 之间")
    @Min(value = 1, message = "端口号必须在 1~65535 之间")
    private Integer port;

    @Length(max = 50, message = "数据库版本最大长度为 50")
    private String version;

    private String userName;
    private String password;



}
