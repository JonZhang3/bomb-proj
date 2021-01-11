package com.bombproj.controller

import java.io.{File, IOException}

import com.bombproj.config.AppProperties
import com.bombproj.framework.JsonResult
import com.bombproj.utils.Utils
import com.queryflow.key.KeyGenerateUtil
import javax.annotation.Resource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, ResponseBody}
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping(Array("/api/upload"))
class UploadController @Resource()(appProperties: AppProperties) {

    @RequestMapping(value = Array("/cover"), method = Array(RequestMethod.POST))
    @ResponseBody
    @throws[IOException]
    def uploadCover(@RequestParam("cover") pic: MultipartFile): JsonResult = {
        if (!pic.isEmpty) {
            val fileSuffix = Utils.getFileSuffix(pic.getOriginalFilename)
            val idFileName = KeyGenerateUtil.generateId + "." + fileSuffix
            val parent = new File(appProperties.getCoverImagePath)
            if (!parent.exists) parent.mkdirs
            val file = new File(parent, idFileName)
            pic.transferTo(file)
            return JsonResult.success("上传成功", idFileName)
        }
        JsonResult.error("上传文件不能为空")
    }

}
