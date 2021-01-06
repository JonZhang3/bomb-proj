package com.bombproj.controller;

import com.bombproj.constants.UserType;
import com.bombproj.dto.LoginDto;
import com.bombproj.framework.CookieConfig;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.WebUtils;
import com.bombproj.model.User;
import com.bombproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JsonResult login(@ModelAttribute @Validated LoginDto model) {
        User user = this.userService.login(model);
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setUserId(user.getId());
        sessionConfig.setUserName(user.getUserName());
        sessionConfig.setNickName(user.getNickName());
        sessionConfig.setUserType(UserType.ofValue(user.getType()));
        WebUtils.setSessionAttribute(SessionConfig.KEY_SESSION_CONFIG, sessionConfig);
        WebUtils.removeSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE);
        if ("yes".equalsIgnoreCase(model.getRemberPwd())) {
            WebUtils.addCookie(CookieConfig.KEY_PWD, model.getPassword(), true);
        } else {
            WebUtils.deleteCookie(CookieConfig.KEY_PWD);
        }
        return JsonResult.success().data(user);
    }

    @GetMapping("/logout")
    public JsonResult logout() {
        WebUtils.removeSessionAttribute(SessionConfig.KEY_SESSION_CONFIG);
        return JsonResult.success();
    }

    @PostMapping("/register")
    public JsonResult register(@ModelAttribute @Validated({LoginDto.Register.class}) LoginDto model) {
        this.userService.register(model);
        return JsonResult.success("注册成功");
    }

}
