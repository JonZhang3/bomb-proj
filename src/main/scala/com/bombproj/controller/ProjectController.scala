package com.bombproj.controller

import com.bombproj.constants.UserType
import com.bombproj.dto.ProjectDto
import com.bombproj.framework.exception.NotFoundException
import com.bombproj.framework.interceptor.AuthPassport
import com.bombproj.framework.{JsonResult, SessionConfig}
import com.bombproj.service.ProjectService
import javax.annotation.Resource
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/api/project"))
class ProjectController @Resource()(projectService: ProjectService) {

    @AuthPassport
    @GetMapping(Array("/permission"))
    def listPermissions: JsonResult =
        JsonResult.success(data = this.projectService.listProjectPermissions)

    @AuthPassport
    @GetMapping(Array(""))
    def listProject(@ModelAttribute dto: ProjectDto,
                    @RequestParam(defaultValue = "3") projectShowType: Int): JsonResult = {
        val sessionConfig = SessionConfig.current()
        if (sessionConfig.userType.equals(UserType.ADMIN)) dto.isAdmin = true
        dto.setUserId(sessionConfig.userId)
        //        if (projectShowType == null) projectShowType = ProjectShowType.CREATE_JOIN.getType
        dto.projectShowType = projectShowType
        JsonResult.success(data = this.projectService.pageListProjects(dto))
    }

    @AuthPassport
    @PostMapping(Array(""))
    def newProject(@ModelAttribute @Validated(Array(classOf[ProjectDto#NewOrUpdateProjectGroup])) dto: ProjectDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.setUserId(sessionConfig.userId)
        this.projectService.newProject(dto)
        JsonResult.success()
    }

    @AuthPassport
    @DeleteMapping(Array("/{projectId}"))
    def deleteProject(@PathVariable("projectId") projectId: String): JsonResult = {
        // TODO check permission
        JsonResult.success()
    }

    @AuthPassport
    @PutMapping(Array("/{projectId}"))
    def updateProject(@PathVariable("projectId") projectId: String,
                      @ModelAttribute @Validated(Array(classOf[ProjectDto#NewOrUpdateProjectGroup])) dto: ProjectDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.setId(projectId)
        dto.setUserId(sessionConfig.userId)
        this.projectService.updateProject(dto)
        JsonResult.success()
    }

    @AuthPassport
    @GetMapping(Array("/{projectId}"))
    def projectDetail(@PathVariable("projectId") projectId: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        val project = this.projectService.projectDetail(projectId, sessionConfig.userId)
        if (project == null) throw new NotFoundException
        else JsonResult.success(data = project)
    }

}
