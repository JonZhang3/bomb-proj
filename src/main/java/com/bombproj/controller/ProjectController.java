package com.bombproj.controller;

import com.bombproj.dto.ProjectDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    @AuthPassport
    public JsonResult list(@ModelAttribute ProjectDto dto,
                           @RequestParam(defaultValue = "3") Integer projectShowType) {
        SessionConfig sessionConfig = SessionConfig.current();

        return null;
    }

}
