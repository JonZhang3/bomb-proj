package com.bombproj.controller

import com.bombproj.dto.ProjectCodeDto
import com.bombproj.framework.JsonResult
import com.bombproj.framework.interceptor.AuthPassport
import com.bombproj.service.ProjectCodeService
import javax.annotation.Resource
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, ModelAttribute, PathVariable, PostMapping, PutMapping, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/project"))
class ProjectCodeController @Resource()(projectCodeService: ProjectCodeService) {

    @AuthPassport
    @GetMapping(Array("/{projectId}/code"))
    def pageListProjectCodes(@PathVariable("projectId") projectId: String,
                             @ModelAttribute dto: ProjectCodeDto): JsonResult = {
        dto.projectId = projectId
        JsonResult.success(data = this.projectCodeService.pageListProjectCodes(dto))
    }

    @AuthPassport
    @PostMapping(Array("/{projectId}/code"))
    def addProjectCode(@PathVariable("projectId") projectId: String,
                       @ModelAttribute @Validated(Array(classOf[ProjectCodeDto#AddOrUpdateProjectCodeGroup])) dto: ProjectCodeDto): JsonResult = {
        dto.projectId = projectId
        this.projectCodeService.addProjectCode(dto)
        JsonResult.success()
    }

    @AuthPassport
    @PutMapping(Array("/{projectId}/code/{codeId}"))
    def updateProjectCode(@PathVariable("projectId") projectId: String,
                          @PathVariable("codeId") codeId: String,
                          @ModelAttribute dto: ProjectCodeDto): JsonResult = {
        dto.projectId = projectId
        dto.id = codeId
        this.projectCodeService.updateProjectCode(dto)
        JsonResult.success()
    }

    @AuthPassport
    @DeleteMapping(Array("/{projectId}/code/{codeId}"))
    def deleteProjectCode(@PathVariable("projectId") projectId: String,
                          @PathVariable("codeId") codeId: String): JsonResult = {
        val dto = new ProjectCodeDto
        dto.projectId = projectId
        dto.id = codeId
        this.projectCodeService.deleteProjectCode(dto)
        JsonResult.success()
    }

}
