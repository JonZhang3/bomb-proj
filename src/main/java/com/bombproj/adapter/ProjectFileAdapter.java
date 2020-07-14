package com.bombproj.adapter;

import com.bombproj.constants.ProjectFileState;
import com.bombproj.constants.ProjectFileType;
import com.bombproj.dto.ProjectFileDto;
import com.bombproj.model.ProjectFile;
import com.queryflow.key.KeyGenerateUtil;

import java.util.Date;

public class ProjectFileAdapter {

    public static ProjectFile createDirFile(ProjectFileDto dto) {
        ProjectFile dir = new ProjectFile();
        dir.setId(KeyGenerateUtil.generateId() + "");
        dir.setFileName(dto.getName());
        dir.setSuffix("");
        dir.setSize(0L);
        dir.setParentId(dto.getFolderId());
        dir.setCreator(dto.getCreatUserId());
        Date now = new Date();
        dir.setCreateTime(now);
        dir.setUpdateTime(now);
        dir.setType(ProjectFileType.DIR.getType());
        dir.setState(ProjectFileState.NORMAL.getState());
        dir.setStarred(0);
        dir.setShared(0);
        dir.setSharedPassword(null);
        dir.setSharedId(null);
        dir.setVersion(1L);
        dir.setProjectId(dto.getProjectId());
        dir.setRecycledBy(null);
        dir.setDeletedBy(null);
        return dir;
    }

}
