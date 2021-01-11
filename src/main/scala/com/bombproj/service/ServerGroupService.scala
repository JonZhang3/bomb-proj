package com.bombproj.service

import com.bombproj.adapter.ServerAdapter
import com.bombproj.dao.ServerGroupDao
import com.bombproj.dto.ServerGroupDto
import com.bombproj.framework.exception.BusinessException
import com.bombproj.model.ServerGroup
import com.queryflow.page.Pager
import com.queryflow.utils.Utils
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class ServerGroupService @Resource()(groupDao: ServerGroupDao) {

    def newGroup(dto: ServerGroupDto): Unit = {
        if (this.groupDao.countGroupByName(dto.groupName, dto.userId) > 0)
            throw new BusinessException(message = "同名主机组已存在")
        this.groupDao.insertGroup(ServerAdapter.getGroupModel(dto))
    }

    def updateGroup(dto: ServerGroupDto): Unit = {
        if (Utils.isEmpty(dto.id)) throw new BusinessException(message = "请选择一个主机组更新")
        this.groupDao.updateGroup(dto)
    }

    def deleteGroup(dto: ServerGroupDto): Unit = {
        if (Utils.isEmpty(dto.id)) throw new BusinessException(message = "请选择一个主机组删除")
        this.groupDao.deleteGroup(dto)
    }

    def pageQuery(dto: ServerGroupDto): Pager[ServerGroup] = {
        if (dto.page <= 0) dto.page = 1
        this.groupDao.pageQuery(dto)
    }

}

//object Main {
//    def main(args: Array[String]): Unit = {
//        val conn = new Connection("")
//        conn.connect()
//        val authResult = conn.authenticateWithPassword("", "")
//    }
//}
