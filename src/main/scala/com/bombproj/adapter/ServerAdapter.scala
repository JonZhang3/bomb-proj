package com.bombproj.adapter

import java.util.Date

import com.bombproj.constants.State
import com.bombproj.dto.{ServerDto, ServerGroupDto}
import com.bombproj.model.{Server, ServerGroup}
import com.queryflow.key.KeyGenerateUtil

object ServerAdapter {

    def getGroupModel(dto: ServerGroupDto): ServerGroup = {
        val group = new ServerGroup
        group.id = s"${KeyGenerateUtil.generateId()}"
        group.groupName = dto.groupName.trim
        group.groupDesc = dto.groupDesc
        group.creator = dto.userId
        group.state = State.COMMON.getState
        val now = new Date
        group.createTime = now
        group.updateTime = now
        group
    }

    def getServerModel(dto: ServerDto): Server = {
        val server = new Server
        server.id = s"${KeyGenerateUtil.generateId()}"
        server.serverName = dto.serverName
        server.hostName = dto.hostName
        server.sshPort = dto.sshPort
        server.describe = dto.describe
        server.username = dto.username
        server.pass = dto.pass
        server.tags = dto.tags
        server.state = State.COMMON.getState
        server.creator = dto.userId
        val now = new Date
        server.createTime = now
        server.updateTime = now
        server
    }

}
