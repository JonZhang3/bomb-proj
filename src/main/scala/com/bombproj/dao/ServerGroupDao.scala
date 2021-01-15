package com.bombproj.dao

import java.util
import java.util.Date

import com.bombproj.constants.State
import com.bombproj.dto.ServerGroupDto
import com.bombproj.model.ServerGroup
import com.bombproj.utils.Utils
import com.queryflow.accessor.A
import com.queryflow.page.Pager
import com.queryflow.sql.SqlBox
import org.springframework.stereotype.Repository

@Repository
class ServerGroupDao {

    def insertGroup(serverGroup: ServerGroup): Unit = {
        SqlBox insert serverGroup
    }

    def updateGroup(dto: ServerGroupDto): Unit = {
        SqlBox.update("server_group")
            .set("groupName", dto.groupName)
            .set("groupDesc", dto.groupDesc)
            .set("updateTime", new Date())
            .where().eq("id", dto.id)
            .execute()
    }

    def deleteGroup(dto: ServerGroupDto): Unit = {
        SqlBox.update("server_group")
            .set("state", State.DELETED.getState.asInstanceOf[Integer])
            .where().eq("id", dto.id)
            .and().eq("creator", dto.userId)
            .execute()
    }

    def deleteRelServerGroupByGroupId(groupId: String): Unit = {
        SqlBox.delete("rel_group_server")
            .where().eq("groupId", groupId)
            .execute()
    }

    def countGroupByName(name: String, userId: String): Int = {
        val sql = "SELECT COUNT(id) FROM server_group WHERE groupName = ? AND creator = ? AND state = ?"
        A.query(sql, name, userId, State.COMMON.getState.asInstanceOf[Integer]).one(classOf[Int])
    }

    def pageQuery(dto: ServerGroupDto): Pager[ServerGroup] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT g.id, g.groupName, g.groupDesc, g.createTime, g.creator, u.nickName userName ")
        sql.append(" FROM server_group g JOIN users u ON g.creator=u.id ")
        sql.append(" WHERE g.state = ? ")
        values.add(State.COMMON.getState.asInstanceOf[Integer])
        if(Utils.isNotEmpty(dto.groupName)) {
            sql.append(" AND g.groupName LIKE ? ")
            values.add(s"%${dto.groupName}%")
        }
        sql.append(" ORDER BY g.createTime DESC ")
        A.page(sql.toString(), values, dto.page, classOf[ServerGroup])
    }

}
