package com.bombproj.service;

import com.bombproj.adapter.ProjectFileAdapter;
import com.bombproj.config.AppProperties;
import com.bombproj.dao.ProjectFilesDao;
import com.bombproj.dto.ProjectFileDto;
import com.bombproj.model.ProjectFile;
import com.bombproj.vo.ProjectAllFileVO;
import com.queryflow.config.GlobalConfig;
import com.queryflow.page.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectFilesService {

    @Autowired
    private ProjectFilesDao projectFilesDao;

    @Autowired
    private AppProperties appProperties;

    public Pager<ProjectAllFileVO> pageProjectFiles(ProjectFileDto dto) {
        Integer page = dto.getPage();
        if(page == null || page <= 0) {
            page = 1;
        }
        dto.setPage(page);
        Integer pageSize = dto.getPageSize();
        if(pageSize == null || pageSize <= 0) {
            pageSize = GlobalConfig.getDefaultPageLimit();
        }
        if(pageSize > appProperties.getMaxPageSize()) {
            pageSize = appProperties.getMaxPageSize();
        }
        dto.setPage(pageSize);
        return this.projectFilesDao.pageQueryAllProjectFiles(dto);
    }

    public void mkdir(ProjectFileDto dto) {
        ProjectFile dir = ProjectFileAdapter.createDirFile(dto);

    }

}
