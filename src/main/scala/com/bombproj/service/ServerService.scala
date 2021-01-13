package com.bombproj.service

import com.bombproj.adapter.ServerAdapter
import com.bombproj.dao.ServerDao
import com.bombproj.dto.ServerDto
import com.bombproj.framework.aop.Transaction
import com.bombproj.framework.exception.BusinessException
import com.bombproj.utils.Utils
import com.bombproj.vo.ServerVO
import com.queryflow.page.Pager
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class ServerService @Resource()(serverDao: ServerDao) {

    @Transaction
    def newServer(dto: ServerDto): Unit = {
        val server = ServerAdapter.getServerModel(dto)
        if(serverDao.countByNameOrHostName(dto.serverName, dto.hostName) > 0)
            throw new BusinessException(message = "存在相同名称或 HostName 的主机")
        // TODO init

        if(serverDao.countByNameOrHostName(dto.serverName, dto.hostName) > 0)
            throw new BusinessException(message = "存在相同名称或 HostName 的主机")
        serverDao.insertServer(server)
        if(Utils.isNotEmpty(dto.serverGroup)) serverDao.insertRelGroupServer(server.id, dto.serverGroup)
    }

    def updateServer(dto: ServerDto): Unit = {
        serverDao.updateServer(dto)
    }

    @Transaction
    def deleteServer(dto: ServerDto): Unit = {
        serverDao.deleteServer(dto.id)
        serverDao.deleteRelServerGroupByServerId(dto.id)
    }

    def removeFromGroup(groupId: String, serverId: String): Unit = {
        serverDao.deleteRelServerGroup(groupId, serverId)
    }

    def pageQuery(dto: ServerDto): Pager[ServerVO] = {
        if (dto.page < 1) dto.page = 1
        serverDao.pageQuery(dto)
    }

}

