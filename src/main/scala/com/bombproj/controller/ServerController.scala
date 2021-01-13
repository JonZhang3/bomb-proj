package com.bombproj.controller

import com.bombproj.dto.{ServerDto, ServerGroupDto}
import com.bombproj.framework.{JsonResult, SessionConfig}
import com.bombproj.service.{ServerGroupService, ServerService}
import javax.annotation.Resource
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, ModelAttribute, PathVariable, PostMapping, PutMapping, RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/api/server"))
class ServerController @Resource() (serverGroupService: ServerGroupService, serverService: ServerService) {

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

    @PutMapping(Array(""))
    def newServer(@ModelAttribute @Validated(Array(classOf[ServerDto#NewOrUpdateServer])) dto: ServerDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.userId = sessionConfig.userId
        serverService.newServer(dto)
        JsonResult.success()
    }

    @PostMapping(Array("/{serverId}"))
    def updateServer(@PathVariable("serverId") serverId: String,
                     @ModelAttribute @Validated(Array(classOf[ServerDto#NewOrUpdateServer])) dto: ServerDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.userId = sessionConfig.userId
        dto.id = serverId
        serverService.updateServer(dto)
        JsonResult.success()
    }

    @DeleteMapping(Array("/{serverId}"))
    def deleteServer(@PathVariable("serverId") serverId: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        val dto = new ServerDto
        dto.id = serverId
        dto.userId = sessionConfig.userId
        serverService.deleteServer(dto)
        JsonResult.success()
    }

    @GetMapping(Array(""))
    def pageQueryServer(@ModelAttribute dto: ServerDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.userId = sessionConfig.userId
        JsonResult.success(data = serverService.pageQuery(dto))
    }

    @DeleteMapping(Array("/{serverId}"))
    def removeFromGroup(@PathVariable("serverId") serverId: String,
                        @RequestParam("groupId") groupId: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        val dto = new ServerDto
        dto.userId = sessionConfig.userId
        serverService.removeFromGroup(groupId, serverId)
        JsonResult.success()
    }

}
