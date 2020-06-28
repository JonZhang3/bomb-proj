package com.bombproj.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ProjectDataTableDto {

    private String projectId;

    private String userId;

    private String tableId;

    @NotEmpty(message = "表名不能为空")
    @Length(max = 50, message = "表名最大长度为 50")
    private String tableName;

    @Length(max = 255, message = "项目名最大长度为 255")
    private String tableDesc;

    @NotEmpty(message = "数据库类型不能为空")
    private String type;

}
