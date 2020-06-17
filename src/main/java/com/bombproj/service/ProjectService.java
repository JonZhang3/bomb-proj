package com.bombproj.service;

import com.bombproj.dao.ProjectDao;
import com.bombproj.dto.ProjectDto;
import com.bombproj.framework.BusinessException;
import com.bombproj.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    public void pageLitProjects(int page) {

    }

    public void deleteProject(ProjectDto project) {
        if(Utils.isEmpty(project.getId())) {
            throw new BusinessException("");
        }
        if("web".equals(project.getId())) {
            throw new BusinessException("系统数据，不允许删除");
        }

    }

}
