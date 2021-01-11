package com.bombproj.controller

import com.bombproj.dto.ProjectFileDto
import com.bombproj.framework.interceptor.AuthPassport
import com.bombproj.framework.{JsonResult, SessionConfig}
import com.bombproj.service.ProjectFilesService
import javax.annotation.Resource
import org.springframework.web.bind.annotation.{GetMapping, ModelAttribute, PathVariable, PostMapping, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/project"))
class ProjectFilesController @Resource()(projectFilesService: ProjectFilesService) {

    @AuthPassport
    @GetMapping(Array("{projectId}/folders/{folderId}"))
    def listFiles(@PathVariable("projectId") projectId: String,
                  @PathVariable("folderId") folderId: String,
                  @ModelAttribute dto: ProjectFileDto): JsonResult = {
        dto.setProjectId(projectId)
        dto.setFolderId(folderId)
        JsonResult.success(data = this.projectFilesService.pageProjectFiles(dto))
    }

    @AuthPassport
    @PostMapping(Array("{projectId}/folders/{folderId}/mkdir"))
    def mkdir(@PathVariable("projectId") projectId: String,
              @PathVariable("folderId") folderId: String,
              @ModelAttribute dto: ProjectFileDto): JsonResult = {
        val sessionConfig = SessionConfig.current
        dto.setProjectId(projectId)
        dto.setFolderId(folderId)
        dto.setCreatUserId(sessionConfig.userId)
        JsonResult.success()
    }

}
