package com.bombproj.controller

import com.bombproj.constants.UserType
import com.bombproj.dto.LoginDto
import com.bombproj.framework.{CookieConfig, JsonResult, SessionConfig, WebUtils}
import com.bombproj.service.UserService
import javax.annotation.Resource
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.{GetMapping, ModelAttribute, PostMapping, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/user"))
class UserController @Resource()(userService: UserService) {

    @PostMapping(Array("/login"))
    def login(@ModelAttribute @Validated model: LoginDto): JsonResult = {
        val user = userService.login(model)
        val sessionConfig = new SessionConfig
        sessionConfig.userId = user.getId
        sessionConfig.userName = user.getUserName
        sessionConfig.nickName = user.getNickName
        sessionConfig.userType = UserType.ofValue(user.getType)
        WebUtils.setSessionAttribute(SessionConfig.KEY_SESSION_CONFIG, sessionConfig)
        WebUtils.removeSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE)
        if ("yes".equalsIgnoreCase(model.getRemberPwd))
            WebUtils.addCookie(CookieConfig.KEY_PWD, model.getPassword, encryption = true)
        else WebUtils.deleteCookie(CookieConfig.KEY_PWD)
        JsonResult.success().data(user)
    }

    @GetMapping(Array("/logout"))
    def logout(): JsonResult = {
        WebUtils.removeSessionAttribute(SessionConfig.KEY_SESSION_CONFIG)
        JsonResult.success()
    }

    @PostMapping(Array("/register"))
    def register(@ModelAttribute @Validated(Array(classOf[LoginDto#Register])) model: LoginDto): JsonResult = {
        this.userService.register(model)
        JsonResult.success("注册成功")
    }

}
