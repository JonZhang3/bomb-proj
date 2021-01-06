package com.bombproj.model;

import com.queryflow.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Table("users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;// ID
    private String userName;// 用户名
    private String password;// 密码
    private String nickName;// 昵称/真实姓名
    private String roleId;// 角色ID
    private String roleName;// 角色名
    private String auth;
    private String authName;
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private Integer state;// 状态 1- 有效
    private Long sequence;// 排序，越大越靠前
    private Byte type;// 用户类型：1普通用户，100：管理员
    private String email;// 邮箱
    private String phone;// 手机号
    private String avatarUrl;// 头像
    private Integer loginType;// 0：账号登陆，1：github登陆，2：码云
    private String thirdlyId;// 第三方唯一ID
    private String passwordSalt;// 密码MD5盐

}
