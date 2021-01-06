package com.bombproj.adapter;

import com.bombproj.constants.ProjectCodeState;
import com.bombproj.dto.ProjectCodeDto;
import com.bombproj.model.ProjectCode;
import com.queryflow.key.KeyGenerateUtil;

import java.util.Date;

public final class ProjectCodeAdapter {

    private ProjectCodeAdapter() {

    }

    public static ProjectCode getModel(ProjectCodeDto dto) {
        ProjectCode model = new ProjectCode();
        Date now = new Date();
        model.setId(KeyGenerateUtil.generateId() + "");
        model.setCode(dto.getCode());
        model.setMessage(dto.getMessage());
        model.setCreateTime(now);
        model.setUpdateTime(now);
        model.setProjectId(dto.getProjectId());
        model.setState(ProjectCodeState.COMMON.getState());
        return model;
    }

}
