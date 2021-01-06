package com.bombproj.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectCodeDto {

    private String id;

    private String projectId;

    @NotBlank(message = "状态码不能为空", groups = AddOrUpdateProjectCodeGroup.class)
    @Length(max = 50, message = "状态码最大长度为 50", groups = AddOrUpdateProjectCodeGroup.class)
    private String code;

    @NotBlank(message = "状态码描述不能为空", groups = AddOrUpdateProjectCodeGroup.class)
    @Length(max = 128, message = "状态码描述最大长度为 128", groups = AddOrUpdateProjectCodeGroup.class)
    private String message;

    private Integer page;

    private String queryText;

    public interface AddOrUpdateProjectCodeGroup {

    }

}
