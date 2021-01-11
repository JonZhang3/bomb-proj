package com.bombproj.service

import java.util
import java.util.Collections

import com.bombproj.adapter.ProjectAdapter
import com.bombproj.constants.{PermissionType, ProjectMemberType}
import com.bombproj.dao.{PermissionDao, ProjectDao}
import com.bombproj.dto.{ProjectDto, ProjectMemberDto}
import com.bombproj.framework.exception.BusinessException
import com.bombproj.model.{Permission, Project}
import com.bombproj.utils.Utils
import com.bombproj.vo.{ProjectMemberListVO, UserQueryResultVO}
import com.queryflow.page.Pager
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class ProjectService @Resource()(projectDao: ProjectDao,
                                 permissionDao: PermissionDao) {

    def pageListProjects(dto: ProjectDto): Pager[Project] = {
        var page = dto.page
        if (page <= 0) page = 1
        dto.page = page
        this.projectDao.pageQueryProjects(dto)
    }

    def deleteProject(projectId: String, userId: String): Unit = {
        if (Utils.isEmpty(projectId)) throw new BusinessException(message = "")
        if ("web" == projectId) throw new BusinessException(message = "系统数据，不允许删除")
        this.projectDao.deleteProject(projectId, userId)
    }

    def newProject(dto: ProjectDto): Unit = {
        if (this.projectDao.countProjectByName(dto.getName, dto.getUserId) > 0)
            throw new BusinessException(message = "该名称的项目已存在")
        this.projectDao.insertProject(ProjectAdapter.getModel(dto))
        // TODO add project creator
    }

    def updateProject(dto: ProjectDto): Unit = {
        this.projectDao.updateProject(dto)
        // TODO log
    }

    def projectDetail(projectId: String, userId: String): Project =
        this.projectDao.queryProjectById(projectId, userId)

    def pageProjectMembers(dto: ProjectMemberDto): Pager[ProjectMemberListVO] = {
        var page = dto.getPage
        if (page <= 0) page = 1
        dto.page = page
        this.projectDao.pageProjectMemebers(dto)
    }

    def queryNotExistsUsers(name: String, projectId: String): util.List[UserQueryResultVO] = {
        if (Utils.isEmpty(name)) return Collections.emptyList[UserQueryResultVO]()
        this.projectDao.queryNotExistsUsers(name, projectId)
    }

    def addProjectMember(projectId: String, userIds: String, permissions: String): Unit = {
        if (Utils.isEmpty(userIds)) throw new BusinessException(message = "请选择要加入的成员")
        if (Utils.isEmpty(permissions)) throw new BusinessException(message = "请选择加入成员的权限")
        val userIdArr = userIds.split(",")
        if (userIdArr.isEmpty) throw new BusinessException(message = "请选择要加入的成员")
        this.projectDao.batchInsertMember(projectId, userIdArr, permissions, ProjectMemberType.MEMBER)
    }

    def listProjectPermissions: util.List[Permission] =
        this.permissionDao.queryAllPermission(PermissionType.PROJECT)

}
