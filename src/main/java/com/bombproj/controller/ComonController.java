package com.bombproj.controller;

import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.WebUtils;
import com.bombproj.utils.ImageCode;
import com.bombproj.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/common")
public class ComonController {

    @RequestMapping("/captcha")
    public void getImageVCode() throws IOException {
        HttpServletResponse response = WebUtils.response();
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageCode imageCode = new ImageCode();
        WebUtils.setSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE, imageCode.getCode());
        try {
            imageCode.write(out);
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping("/checkCaptcha")
    @ResponseBody
    public JsonResult checkVerificationCode(@RequestParam("verificationCode") String verificationCode, HttpServletRequest request) {
        if (Utils.isEmpty(verificationCode)) {
            return JsonResult.error("验证码有误");
        }
        if (verificationCode.equals(WebUtils.getSessionAttribute(SessionConfig.KEY_VERIFICATION_CODE))) {
            return JsonResult.success("通过");
        }
        return JsonResult.error("验证码有误");
    }

}
