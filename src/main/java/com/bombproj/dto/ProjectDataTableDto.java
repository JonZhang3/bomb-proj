package com.bombproj.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ProjectDataTableDto {

    private String projectId;

    private String userId;

    private String tableId;

    private Long databaseId;

    @NotEmpty(message = "表名不能为空")
    @Length(max = 50, message = "表名最大长度为 50")
    private String tableName;

    @Length(max = 255, message = "描述最大长度为 255")
    private String tableDesc;

}
