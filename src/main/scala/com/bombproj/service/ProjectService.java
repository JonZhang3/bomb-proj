package com.bombproj.service;

import com.bombproj.adapter.ProjectAdapter;
import com.bombproj.constants.PermissionType;
import com.bombproj.constants.ProjectMemberType;
import com.bombproj.dao.PermissionDao;
import com.bombproj.dao.ProjectDao;
import com.bombproj.dto.ProjectDto;
import com.bombproj.dto.ProjectMemberDto;
import com.bombproj.framework.exception.BusinessException;
import com.bombproj.model.Permission;
import com.bombproj.model.Project;
import com.bombproj.utils.Utils;
import com.bombproj.vo.ProjectMemberListVO;
import com.bombproj.vo.UserQueryResultVO;
import com.queryflow.page.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private PermissionDao permissionDao;

    public Pager<Project> pageLitProjects(ProjectDto dto) {
        Integer page = dto.getPage();
        if (page == null || page <= 0) {
            page = 1;
        }
        dto.setPage(page);
        return this.projectDao.pageQueryProjects(dto);
    }

    public void deleteProject(String projectId, String userId) {
        if (Utils.isEmpty(projectId)) {
            throw new BusinessException("");
        }
        if ("web".equals(projectId)) {
            throw new BusinessException("系统数据，不允许删除");
        }
        this.projectDao.deleteProject(projectId, userId);
    }

    public void newProject(ProjectDto dto) {
        if (this.projectDao.countProjectByName(dto.getName(), dto.getUserId()) > 0) {
            throw new BusinessException("该名称的项目已存在");
        }
        this.projectDao.insertProject(ProjectAdapter.getModel(dto));
        // TODO add project creator

    }

    public void updateProject(ProjectDto dto) {
        this.projectDao.updateProject(dto);
        // TODO log
    }

    public Project projectDetail(String projectId, String userId) {
        return this.projectDao.queryProjectById(projectId, userId);
    }

    public Pager<ProjectMemberListVO> pageProjectMembers(ProjectMemberDto dto) {
        Integer page = dto.getPage();
        if (page == null || page <= 0) {
            page = 1;
        }
        dto.setPage(page);
        return this.projectDao.pageProjectMemebers(dto);
    }

    public List<UserQueryResultVO> queryNotExistsUsers(String name, String projectId) {
        if (Utils.isEmpty(name)) {
            return Collections.emptyList();
        }
        return this.projectDao.queryNotExistsUsers(name, projectId);
    }

    public void addProjectMember(String projectId, String userIds, String permissions) {
        if(Utils.isEmpty(userIds)) {
            throw new BusinessException("请选择要加入的成员");
        }
        if(Utils.isEmpty(permissions)) {
            throw new BusinessException("请选择加入成员的权限");
        }
        String[] userIdArr = userIds.split(",");
        if(userIdArr.length == 0) {
            throw new BusinessException("请选择要加入的成员");
        }
        this.projectDao.batchInsertMember(projectId, userIdArr, permissions, ProjectMemberType.MEMBER);
    }

    public List<Permission> listProjectPermissions() {
        return this.permissionDao.queryAllPermission(PermissionType.PROJECT);
    }

}
