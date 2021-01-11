package com.bombproj.dao

import java.util
import java.util.Date

import com.bombproj.constants.ProjectCodeState
import com.bombproj.dto.ProjectCodeDto
import com.bombproj.model.ProjectCode
import com.bombproj.utils.Utils
import com.bombproj.vo.ProjectCodeVO
import com.queryflow.accessor.A
import com.queryflow.page.Pager
import com.queryflow.sql.SqlBox
import org.springframework.stereotype.Repository

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.JavaConverters._

@Repository
class ProjectCodeDao {

    def pageQueryProjectCodes(dto: ProjectCodeDto): Pager[ProjectCodeVO] = {
        val sql = new StringBuilder
        val values = new ArrayBuffer[Object]
        sql.append(" SELECT id, code, message, createTime FROM project_code WHERE state = ? AND projectId = ? ")
        values += ProjectCodeState.COMMON.getState.asInstanceOf[Integer]
        values += dto.projectId
        if (Utils.isNotEmpty(dto.getCode)) {
            sql.append(" AND code LIKE ? ")
            values += s"%${dto.getCode}%"
        }
        if (Utils.isNotEmpty(dto.getMessage)) {
            sql.append(" AND message LIKE ? ")
            values += s"%${dto.message}%"
        }
        if (Utils.isNotEmpty(dto.getQueryText)) {
            sql.append(" AND (code LIKE ? OR message LIKE ?) ")
            values += s"%${dto.getQueryText}%"
            values += s"%${dto.getQueryText}%"
        }
        sql.append(" ORDER BY createTime DESC ")
        A.page(sql.toString, values.asJava, dto.getPage, classOf[ProjectCodeVO])
    }

    def countProjectCodeByCode(code: String, projectId: String): Integer = {
        val sql = "SELECT COUNT(id) FROM project_code WHERE code = ? AND projectId = ?"
        A.query(sql, code, projectId).one(classOf[Integer])
    }

    def insertProjectCode(projectCode: ProjectCode): Unit = {
        SqlBox.insert(projectCode)
    }

    def updateProjectId(dto: ProjectCodeDto): Unit = {
        SqlBox.update("project_code")
            .set("code", dto.getCode)
            .set("message", dto.getMessage)
            .set("updateTime", new Date)
            .where.eq("projectId", dto.getProjectId)
            .and.eq("id", dto.getId)
            .execute
    }

    def deleteProjectId(id: String, projectId: String): Unit = {
        SqlBox.delete("project_code")
            .where.eq("projectId", projectId)
            .and.eq("id", id)
            .execute
    }

}
