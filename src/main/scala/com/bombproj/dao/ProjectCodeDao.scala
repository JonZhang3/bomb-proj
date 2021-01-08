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

@Repository
class ProjectCodeDao {

    def pageQueryProjectCodes(dto: ProjectCodeDto): Pager[ProjectCodeVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT id, code, message, createTime FROM project_code WHERE state = ? AND projectId = ? ")
        values.add(ProjectCodeState.COMMON.getState)
        values.add(dto.getProjectId)
        if (Utils.isNotEmpty(dto.getCode)) {
            sql.append(" AND code LIKE ? ")
            values.add(s"%${dto.getCode}%")
        }
        if (Utils.isNotEmpty(dto.getMessage)) {
            sql.append(" AND message LIKE ? ")
            values.add(s"%${dto.message}%")
        }
        if (Utils.isNotEmpty(dto.getQueryText)) {
            sql.append(" AND (code LIKE ? OR message LIKE ?) ")
            values.add(s"%${dto.getQueryText}%")
            values.add(s"%${dto.getQueryText}%")
        }
        sql.append(" ORDER BY createTime DESC ")
        A.page(sql.toString, values, dto.getPage, classOf[ProjectCodeVO])
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
