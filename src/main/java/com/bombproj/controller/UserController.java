package com.bombproj.controller;

import com.bombproj.dto.LoginDto;
import com.bombproj.framework.BusinessException;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.WebUtils;
import com.bombproj.model.User;
import com.bombproj.service.UserService;
import com.bombproj.utils.Utils;
import com.mysql.cj.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "1.0.0", value = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录（DONE）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userName", value = "用户名/邮箱/手机号", required = true, dataType = "String"),
        @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "remberPwd", value = "是否记住密码")
    })
    @PostMapping("/login")
    public JsonResult login(@ModelAttribute @Validated LoginDto model) {
        User user = this.userService.login(model);
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setUserId(user.getId());
        sessionConfig.setUserName(user.getUserName());
        sessionConfig.setNickName(user.getNickName());
        WebUtils.setSessionAttribute(SessionConfig.KEY_SESSION_CONFIG, sessionConfig);
        WebUtils.removeSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE);
        if("yes".equalsIgnoreCase(model.getRemberPwd())) {
            // TODO
            WebUtils.addCookie("", model.getPassword(), true);
        } else {
            WebUtils.deleteCookie("");
        }
        return JsonResult.success().data(user);
    }

    @GetMapping("/logout")
    public JsonResult logout() {
        WebUtils.removeSessionAttribute(SessionConfig.KEY_SESSION_CONFIG);
        return JsonResult.success();
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public JsonResult register(@ModelAttribute @Validated({LoginDto.Register.class}) LoginDto model) {
        this.userService.register(model);
        return JsonResult.success("注册成功");
    }

}
