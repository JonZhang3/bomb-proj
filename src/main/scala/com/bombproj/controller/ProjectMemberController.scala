package com.bombproj.controller

import com.bombproj.dto.ProjectMemberDto
import com.bombproj.framework.interceptor.AuthPassport
import com.bombproj.framework.{JsonResult, SessionConfig}
import com.bombproj.service.ProjectService
import javax.annotation.Resource
import org.springframework.web.bind.annotation.{GetMapping, ModelAttribute, PathVariable, PostMapping, RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/api/project"))
class ProjectMemberController @Resource()(projectService: ProjectService) {

    @AuthPassport
    @GetMapping(Array("/{projectId}/member"))
    def listProjectMembers(@PathVariable("projectId") projectId: String,
                           @ModelAttribute dto: ProjectMemberDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.setProjectId(projectId)
        dto.setCreateUserId(sessionConfig.userId)
        JsonResult.success(data = this.projectService.pageProjectMembers(dto))
    }

    @AuthPassport
    @PostMapping(Array("/{projectId}/member"))
    def addProjectMembers(@PathVariable("projectId") projectId: String,
                          @RequestParam("userIds") userIds: String,
                          @RequestParam("permissions") permissions: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        // TODO check permission
        this.projectService.addProjectMember(projectId, userIds, permissions)
        JsonResult.success()
    }

    @AuthPassport
    @GetMapping(Array("/{projectId}/not-exists-users"))
    def queryNotExistsUsers(@RequestParam("name") name: String,
                            @PathVariable("projectId") projectId: String): JsonResult =
        JsonResult.success(data = this.projectService.queryNotExistsUsers(name, projectId))

}
