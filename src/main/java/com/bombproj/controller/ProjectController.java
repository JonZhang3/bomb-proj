package com.bombproj.controller;

import com.bombproj.constants.ProjectShowType;
import com.bombproj.constants.UserType;
import com.bombproj.dto.ProjectDto;
import com.bombproj.dto.ProjectMemberDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.exception.NotFoundException;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.model.Project;
import com.bombproj.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @AuthPassport
    @GetMapping("/permission")
    public JsonResult listPermissions() {
        return JsonResult.success(this.projectService.listProjectPermissions());
    }

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
    public JsonResult newProject(@ModelAttribute @Validated(ProjectDto.NewOrUpdateProjectGroup.class) ProjectDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setUserId(sessionConfig.getUserId());
        this.projectService.newProject(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @DeleteMapping("/{projectId}")
    public JsonResult deleteProject(@PathVariable("projectId") String projectId) {
        // TODO check permission

        return null;
    }

    @AuthPassport
    @PutMapping("/{projectId}")
    public JsonResult updateProject(@PathVariable("projectId") String projectId,
                                    @ModelAttribute @Validated(ProjectDto.NewOrUpdateProjectGroup.class) ProjectDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        // TODO check permission
        dto.setId(projectId);
        dto.setUserId(sessionConfig.getUserId());
        this.projectService.updateProject(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @GetMapping("/{projectId}")
    public JsonResult projectDetail(@PathVariable("projectId") String projectId) {
        SessionConfig sessionConfig = SessionConfig.current();
        // TODO check permission
        Project project = this.projectService.projectDetail(projectId, sessionConfig.getUserId());
        if(project == null) {
            throw new NotFoundException();
        } else {
            return JsonResult.success(project);
        }
    }

}
