package com.bombproj.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "昵称不能为空", groups = Register.class)
    private String nickName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能少于 6 位", groups = Register.class)
    private String password;

    private String rpassword;

    private String remberPwd;

    @NotBlank(message = "验证码有误")
    private String verificationCode;

    private String sessionAdminName; // 如果sessionAdminName 不为null，则表示已经登录

    private String tipMessage;

    @Email(message = "邮箱格式错误", groups = Register.class)
    @NotBlank(message = "邮箱不能为空", groups = Register.class)
    private String email;

    @NotBlank(message = "手机号不能为空", groups = Register.class)
    private String phone;

    private String attributes;

    public interface Register {
    }

}
