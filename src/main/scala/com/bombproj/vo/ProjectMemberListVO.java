package com.bombproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectMemberListVO {

    private String id;
    private String userId;
    private String permission;
    private String userName;
    private String nickName;
    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

}
