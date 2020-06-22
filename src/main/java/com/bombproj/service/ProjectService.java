package com.bombproj.service;

import com.bombproj.adapter.ProjectAdapter;
import com.bombproj.dao.ProjectDao;
import com.bombproj.dto.ProjectDto;
import com.bombproj.dto.ProjectMemberDto;
import com.bombproj.framework.BusinessException;
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

}
