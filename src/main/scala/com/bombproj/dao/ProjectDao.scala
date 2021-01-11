package com.bombproj.dao

import java.util
import java.util.Date

import com.bombproj.constants.{ProjectMemberState, ProjectMemberType, ProjectState, UserState}
import com.bombproj.dto.{ProjectDto, ProjectMemberDto}
import com.bombproj.model.Project
import com.bombproj.utils.Utils
import com.bombproj.vo.{ProjectMemberListVO, UserQueryResultVO}
import com.queryflow.accessor.A
import com.queryflow.key.KeyGenerateUtil
import com.queryflow.page.Pager
import com.queryflow.sql.SqlBox
import org.springframework.stereotype.Repository

@Repository
class ProjectDao {

    def pageQueryProjects(query: ProjectDto): Pager[Project] = {
        val values = new util.LinkedList[AnyRef]
        val sql = new StringBuilder
        sql.append(" SELECT p.id, p.projectName, p.projectDesc, p.createTime, p.gitAddr, ")
        sql.append(" p.cover, p.uniKey, p.type, p.userId, u.nickName userName FROM project p ")
        sql.append(" JOIN users u ON p.userId = u.id ")
        sql.append(" WHERE p.state = ? ")
        values.add(ProjectState.COMMON.getState.asInstanceOf[Integer])
        if (Utils.isNotEmpty(query.getName)) {
            sql.append(" AND p.projectName LIKE ? ")
            values.add(s"%${query.getName}%")
        }
        if (!query.isAdmin) {
            sql.append(" AND p.userId = ? ")
            values.add(query.getUserId)
        }
        sql.append(" ORDER BY p.createTime DESC ")
        A.page(sql.toString, values, query.getPage, classOf[Project])
    }

    /**
     * 查询改用户下是否有相同名称的项目
     *
     * @param projectName  项目名称
     * @param createUserId 创建用户 ID
     * @return 返回指定名称项目的数量
     */
    def countProjectByName(projectName: String, createUserId: String): Int = {
        val sql = "SELECT COUNT(id) FROM project WHERE projectName = ? AND userId = ?"
        A.query(sql, projectName, createUserId).one(classOf[Int])
    }

    def insertProject(project: Project): Unit = {
        SqlBox insert project
    }

    def deleteProject(projectId: String, userId: String): Unit = {
        val sql = "UPDATE project SET state = ? WHERE userId = ? AND id = ?"
        A.update(sql, ProjectState.DELETED.getState.asInstanceOf[Integer], userId, projectId)
    }

    def updateProject(dto: ProjectDto): Unit = {
        SqlBox.update("project")
            .set("projectName", dto.getName)
            .set("projectDesc", dto.getDesc)
            .set("type", dto.getType)
            .set("cover", dto.getCover)
            .set("gitAddr", dto.getGitAddr)
            .set("updateTime", new Date)
            .where.eq("id", dto.getId)
            .and.eq("userId", dto.getUserId)
            .execute
    }

    def queryProjectById(projectId: String, userId: String): Project = {
        val sql = "SELECT p.id, p.projectName, p.projectDesc, p.createTime, p.updateTime, " +
            "p.gitAddr,p.cover, p.uniKey, p.type, p.userId, u.nickName userName " +
            "FROM project p JOIN users u ON p.userId=u.id WHERE p.id = ? AND p.userId = ?"
        A.query(sql, projectId, userId).one(classOf[Project])
    }

    def pageProjectMemebers(dto: ProjectMemberDto): Pager[ProjectMemberListVO] = {
        val sql = new StringBuilder
        sql.append(" SELECT m.id, m.userId, m.permission, m.createTime, u.userName, u.nickName, u.email, u.phone ")
        sql.append(" FROM project_member m JOIN project p ON m.projectId = p.id ")
        sql.append(" JOIN users u ON m.userId = u.id ")
        sql.append(" WHERE u.state = ? AND p.userId = ? AND m.state = ? ")
        val values = new util.LinkedList[AnyRef]
        values.add(UserState.VALID.getCode.asInstanceOf[Integer])
        values.add(dto.createUserId)
        values.add(ProjectMemberState.NORMAL.getState.asInstanceOf[Integer])
        if (Utils.isNotEmpty(dto.userName)) {
            sql.append(" AND u.nickName LIKE ? ")
            values.add(s"%${dto.userName}%")
        }
        sql.append(" AND m.projectId = ? ")
        values.add(dto.projectId)
        sql.append(" ORDER BY m.createTime DESC ")
        A.page(sql.toString, values, dto.getPage, classOf[ProjectMemberListVO])
    }

    def queryNotExistsUsers(name: String, projectId: String): util.List[UserQueryResultVO] = {
        val sqlPrefix = "SELECT u.id, u.userName, u.nickName, u.email, u.phone FROM users u WHERE NOT EXISTS " +
            "(SELECT * FROM project_member m WHERE m.userId = u.id AND m.projectId = ? ) AND u.state = ? "
        val sql = s"$sqlPrefix AND u.userName LIKE ?  UNION $sqlPrefix AND u.nickName LIKE ?  UNION $sqlPrefix AND u.email LIKE ?  UNION $sqlPrefix AND u.phone LIKE ? "
        A.query(sql, projectId, UserState.VALID.getCode.asInstanceOf[Integer], s"%$name%",
            projectId, UserState.VALID.getCode.asInstanceOf[Integer], s"%$name%",
            projectId, UserState.VALID.getCode.asInstanceOf[Integer], s"%$name%",
            projectId, UserState.VALID.getCode.asInstanceOf[Integer], s"%$name%")
            .list(classOf[UserQueryResultVO])
    }

    def batchInsertMember(projectId: String, userIds: Array[String], permissions: String, memberType: ProjectMemberType): Unit = {
        val sql = "INSERT INTO project_member SELECT " +
            "?, ?, ?, ?, ?, ?, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM project_member WHERE " +
            "projectId = ? AND userId = ?)"
        val values = new util.LinkedList[util.List[AnyRef]]
        val now = new Date
        for (userId <- userIds) {
            if (Utils.isNotEmpty(userId)) {
                val value = new util.LinkedList[AnyRef]
                value.add(KeyGenerateUtil.generateId + "")
                value.add(ProjectMemberState.NORMAL.getState.asInstanceOf[Integer])
                value.add("0")
                value.add(now)
                value.add(now)
                value.add(projectId)
                value.add(userId)
                value.add(permissions)
                value.add(memberType.getType.asInstanceOf[Integer])
                value.add(projectId)
                value.add(userId)
                values.add(value)
            }
        }
        A.batch(sql, values)
    }

}
