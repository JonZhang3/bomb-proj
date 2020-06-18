package com.bombproj.controller;

import com.bombproj.constants.ProjectShowType;
import com.bombproj.constants.UserType;
import com.bombproj.dto.ProjectDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @AuthPassport
    @GetMapping("")
    public JsonResult listProject(@ModelAttribute ProjectDto dto,
                           @RequestParam(defaultValue = "3") Integer projectShowType) {
        SessionConfig sessionConfig = SessionConfig.current();
        if(sessionConfig.getUserType().equals(UserType.ADMIN)) {
            dto.setAdmin(true);
        }
        dto.setUserId(sessionConfig.getUserId());
        if(projectShowType == null) {
            projectShowType = ProjectShowType.CREATE_JOIN.getType();
        }
        dto.setProjectShowType(projectShowType);
        return JsonResult.success(this.projectService.pageLitProjects(dto));
    }

    @AuthPassport
    @PostMapping("")
    public JsonResult newProject(@ModelAttribute @Validated(ProjectDto.NewProjectGroup.class) ProjectDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setUserId(sessionConfig.getUserId());
        dto.setUserName(sessionConfig.getNickName());
        this.projectService.newProject(dto);
        return JsonResult.success();
    }

    public JsonResult delete(@ModelAttribute ProjectDto project) {
        return null;
    }

}
