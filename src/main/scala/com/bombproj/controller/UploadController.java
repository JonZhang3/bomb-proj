package com.bombproj.controller;

import com.bombproj.config.AppProperties;
import com.bombproj.framework.JsonResult;
import com.queryflow.key.KeyGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private AppProperties appProperties;

    @RequestMapping(value = "/cover", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadCover(@RequestParam("cover") MultipartFile pic) throws IOException {
        if(!pic.isEmpty()) {
            String fileSuffix = Utils.getFileSuffix(pic.getOriginalFilename());
            String idFileName = KeyGenerateUtil.generateId() + "." + fileSuffix;
            File parent = new File(appProperties.getCoverImagePath());
            if(!parent.exists()) {
                parent.mkdirs();
            }
            File file = new File(parent, idFileName);
            pic.transferTo(file);
            return JsonResult.success("上传成功", idFileName);
        }
        return JsonResult.error("上传文件不能为空");
    }

}
