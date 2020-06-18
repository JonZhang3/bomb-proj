package com.bombproj.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class ProjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = "项目名不能为空", groups = NewProjectGroup.class)
    @Length(max = 50, message = "项目名最大长度为 50", groups = NewProjectGroup.class)
    private String name;

    @Length(max = 255, message = "项目简介最大长度为 255", groups = NewProjectGroup.class)
    private String desc;

    private Integer type;

    private Integer page;

    private String cover;

    @URL.List({
        @URL(protocol = "http"),
        @URL(protocol = "https")
    })
    private String gitAddr;

    private String userId;
    private String userName;

    // query
    private Integer projectShowType;
    private boolean isAdmin;

    public interface NewProjectGroup {

    }

}
