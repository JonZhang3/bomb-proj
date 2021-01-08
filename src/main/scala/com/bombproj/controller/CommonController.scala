package com.bombproj.controller

import com.bombproj.framework.{JsonResult, SessionConfig, WebUtils}
import com.bombproj.utils.{ImageCode, Utils}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, ResponseBody}

@Controller
@RequestMapping(Array("/api/common"))
class CommonController {

    @RequestMapping(Array("/captcha"))
    def getImageVCode(): Unit = {
        val response = WebUtils.response
        response.setDateHeader("Expires", 0)
        response.addHeader("Pragma", "no-cache")
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0")
        response.setContentType("image/jpeg")
        val stream = response.getOutputStream
        val imageCode = new ImageCode()
        WebUtils.setSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE, imageCode.getCode)
        try {
            imageCode.write(stream)
        } finally {
            stream.close()
        }
    }

    @RequestMapping(Array("/checkCaptcha"))
    @ResponseBody
    def checkVerificationCode(@RequestParam("verificationCode") verificationCode: String): JsonResult = {
        if (Utils.isEmpty(verificationCode)) JsonResult.error("验证码有误")
        if (verificationCode == WebUtils.getSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE)) JsonResult.success("通过")
        JsonResult.error("验证码有误")
    }

}
