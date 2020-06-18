package com.bombproj.service;

import com.bombproj.adapter.ProjectAdapter;
import com.bombproj.dao.ProjectDao;
import com.bombproj.dto.ProjectDto;
import com.bombproj.framework.BusinessException;
import com.bombproj.model.Project;
import com.bombproj.utils.Utils;
import com.queryflow.page.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    public Pager<Project> pageLitProjects(ProjectDto dto) {
        Integer page = dto.getPage();
        if(page == null || page <= 0) {
            page = 1;
        }
        dto.setPage(page);
        return this.projectDao.pageQueryProjects(dto);
    }

    public void deleteProject(ProjectDto project) {
        if(Utils.isEmpty(project.getId())) {
            throw new BusinessException("");
        }
        if("web".equals(project.getId())) {
            throw new BusinessException("系统数据，不允许删除");
        }

    }

    public void newProject(ProjectDto dto) {
        if(this.projectDao.countProjectByName(dto.getName(), dto.getUserId()) > 0) {
            throw new BusinessException("该名称的项目已存在");
        }
        this.projectDao.insertProject(ProjectAdapter.getModel(dto));
    }

}
