package com.bombproj.dao

import java.util
import java.util.Date

import com.bombproj.constants.State
import com.bombproj.dto.ServerDto
import com.bombproj.model.Server
import com.bombproj.utils.Utils
import com.bombproj.vo.ServerVO
import com.queryflow.accessor.A
import com.queryflow.page.Pager
import com.queryflow.sql.SqlBox
import org.springframework.stereotype.Repository

@Repository
class ServerDao {

    def insertServer(server: Server): Unit = {
        SqlBox.insert(server)
    }

    def insertRelGroupServer(serverId: String, groupId: String): Unit = {
        val sql = "INSERT INTO rel_group_server (groupId, serverId) SELECT ?,? FROM DUAL WHERE NOT EXISTS "+
            "(SELECT groupId, serverId FROM rel_group_server WHERE groupId = ? AND serverId = ?)"
        A.update(sql, groupId, serverId, groupId, serverId)
    }

    def countByNameOrHostName(serverName: String, hostName: String): Int = {
        val sql = "SELECT COUNT(id) FROM servers WHERE serverName = ? OR hostName = ?";
        A.query(sql, serverName, hostName).one(classOf[Int])
    }

    def updateServer(dto: ServerDto): Unit = {
        SqlBox.update("servers")
            .set("serverName", dto.serverName)
            .set("hostName", dto.hostName)
            .set("sshPort", dto.sshPort)
            .set("describe", dto.description)
            .set("username", dto.username)
            .set("pass", dto.pass)
            .set("tags", dto.tags)
            .set("updateTime", new Date())
            .where().eq("id", dto.id)
            .and().eq("state", State.DELETED.getState.asInstanceOf[Integer])
            .execute()
    }

    def deleteServer(id: String): Unit = {
        SqlBox.update("servers")
            .set("state", State.DELETED.getState.asInstanceOf[Integer])
            .set("updateTime", new Date())
            .where().eq("id", id)
            .execute()
    }

    def deleteRelServerGroupByServerId(id: String): Unit = {
        SqlBox.delete("rel_group_server")
            .where().eq("serverId", id)
            .execute()
    }

    def deleteRelServerGroup(groupId: String, serverId: String): Unit = {
        SqlBox.delete("rel_group_server")
            .where().eq("groupId", groupId)
            .and().eq("serverId", serverId)
            .execute()
    }

    def pageQuery(dto: ServerDto): Pager[ServerVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT s.id, s.serverName, s.hostName, s.sshPort, s.description, s.username, ")
        sql.append("s.tags, u.nickName createUserName")
        sql.append(" FROM servers s JOIN users u ON s.creator=u.id ")
        sql.append(" WHERE s.state = ? ")
        values.add(State.COMMON.getState.asInstanceOf[Integer])
        if(Utils.isNotEmpty(dto.name)) {
            sql.append(" AND (s.serverName LIKE ? OR s.hostName LIKE ?) ")
            values.add(s"%${dto.name}%")
            values.add(s"%${dto.name}%")
        }
        sql.append(" ORDER BY s.createTime DESC ")
        A.page(sql.toString(), values, dto.page, classOf[ServerVO])
    }

}
