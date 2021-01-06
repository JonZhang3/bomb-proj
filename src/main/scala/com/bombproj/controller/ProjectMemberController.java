package com.bombproj.controller;

import com.bombproj.dto.ProjectMemberDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectMemberController {

    @Autowired
    private ProjectService projectService;

    @AuthPassport
    @GetMapping("/{projectId}/member")
    public JsonResult listProjectMembers(@PathVariable("projectId") String projectId,
                                         @ModelAttribute ProjectMemberDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setProjectId(projectId);
        dto.setCreateUserId(sessionConfig.getUserId());
        return JsonResult.success(this.projectService.pageProjectMembers(dto));
    }

    @AuthPassport
    @PostMapping("/{projectId}/member")
    public JsonResult addProjectMembers(@PathVariable("projectId") String projectId,
                                        @RequestParam("userIds") String userIds,
                                        @RequestParam("permissions") String permissions) {
        SessionConfig sessionConfig = SessionConfig.current();
        // TODO check permission
        this.projectService.addProjectMember(projectId, userIds, permissions);
        return JsonResult.success();
    }

    @AuthPassport
    @GetMapping("/{projectId}/not-exists-users")
    public JsonResult queryNotExistsUsers(@RequestParam("name") String name,
                                          @PathVariable("projectId") String projectId) {
        return JsonResult.success(this.projectService.queryNotExistsUsers(name, projectId));
    }

}
