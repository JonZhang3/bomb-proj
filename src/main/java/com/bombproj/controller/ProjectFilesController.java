package com.bombproj.controller;

import com.bombproj.framework.JsonResult;
import com.bombproj.framework.interceptor.AuthPassport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/project")
public class ProjectFilesController {

    @AuthPassport
    @GetMapping("{projectId}/")
    public JsonResult listFiles() {
        return null;
    }

}
