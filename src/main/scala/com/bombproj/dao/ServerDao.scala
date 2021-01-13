package com.bombproj.dao

import java.util

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

    def countByNameOrHostName(serverName: String, hostName: String): Int = {
        val sql = "SELECT COUNT(id) FROM servers WHERE serverName = ? OR hostName = ?";
        A.query(sql, serverName, hostName).one[Int]
    }

    def deleteServer(): Unit = {
        SqlBox.update("servers")
            .set("state", )
    }

    def pageQuery(dto: ServerDto): Pager[ServerVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT s.id, s.serverName, s.hostName, s.sshPort, s.describe, s.username, ")
        sql.append("s.tags, u.nickName createUserName")
        sql.append(" FROM servers s JOIN users u ON s.creator=u.id ")
        sql.append(" s.state = ? ")
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
