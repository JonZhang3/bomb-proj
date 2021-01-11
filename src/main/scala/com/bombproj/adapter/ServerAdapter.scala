package com.bombproj.adapter

import java.util.Date

import com.bombproj.constants.State
import com.bombproj.dto.ServerGroupDto
import com.bombproj.model.ServerGroup
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

}
