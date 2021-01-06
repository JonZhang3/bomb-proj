package com.bombproj.adapter;

import com.bombproj.constants.ProjectState;
import com.bombproj.constants.ProjectType;
import com.bombproj.dto.ProjectDto;
import com.bombproj.model.Project;
import com.bombproj.utils.ShortID;
import com.queryflow.key.KeyGenerateUtil;

import java.util.Date;

public final class ProjectAdapter {

    private ProjectAdapter() {

    }

    public static Project getModel(ProjectDto dto) {
        Project project = new Project();
        project.setId(KeyGenerateUtil.generateId() + "");
        project.setUniKey(ShortID.randomID());
        project.setProjectName(dto.getName());
        project.setProjectDesc(dto.getDesc());
        Date now = new Date();
        project.setCreateTime(now);
        project.setUpdateTime(now);
        project.setState(ProjectState.COMMON.getState());
        project.setSequence(0L);
        project.setRemark("");
        project.setUserId(dto.getUserId());
        project.setType(dto.getType() == null ? ProjectType.PUBLIC.getType() : dto.getType());
        project.setCover(dto.getCover());
        project.setGitAddr(dto.getGitAddr());
        return project;
    }

}
