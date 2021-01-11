package com.bombproj.controller

import com.bombproj.dto.ServerGroupDto
import com.bombproj.framework.{JsonResult, SessionConfig}
import com.bombproj.service.ServerGroupService
import javax.annotation.Resource
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, ModelAttribute, PathVariable, PostMapping, PutMapping, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/server"))
class ServerController @Resource() (serverGroupService: ServerGroupService) {

    @PutMapping(Array("/group"))
    def newGroup(@ModelAttribute @Validated(Array(classOf[ServerGroupDto#NewOrUpdateGroup])) dto: ServerGroupDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.userId = sessionConfig.userId
        serverGroupService.newGroup(dto)
        JsonResult.success()
    }

    @PostMapping(Array("/group/{groupId}"))
    def updateGroup(@PathVariable("groupId") groupdId: String,
                    @ModelAttribute @Validated(Array(classOf[ServerGroupDto#NewOrUpdateGroup])) dto: ServerGroupDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.userId = sessionConfig.userId
        dto.id = groupdId
        this.serverGroupService.updateGroup(dto)
        JsonResult.success()
    }

    @DeleteMapping(Array("/group/{groupId}"))
    def deleteGroup(@PathVariable("groupId") groupdId: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        val dto = new ServerGroupDto
        dto.id = groupdId
        dto.userId = sessionConfig.userId
        this.serverGroupService.deleteGroup(dto)
        JsonResult.success()
    }

    @GetMapping(Array("/group"))
    def pageQueryGroup(dto: ServerGroupDto): JsonResult = {
        JsonResult.success(data = this.serverGroupService.pageQuery(dto))
    }

    

}
