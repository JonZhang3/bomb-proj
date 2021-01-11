package com.bombproj.service

import com.bombproj.adapter.ProjectFileAdapter
import com.bombproj.config.AppProperties
import com.bombproj.dao.ProjectFilesDao
import com.bombproj.dto.ProjectFileDto
import com.bombproj.model.ProjectFile
import com.bombproj.vo.ProjectAllFileVO
import com.queryflow.config.GlobalConfig
import com.queryflow.page.Pager
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class ProjectFilesService @Resource() (projectFilesDao: ProjectFilesDao,
                                       appProperties: AppProperties) {

    def pageProjectFiles(dto: ProjectFileDto): Pager[ProjectAllFileVO] = {
        var page = dto.getPage
        if (page <= 0) page = 1
        dto.page = page
        var pageSize = dto.getPageSize
        if (pageSize <= 0) pageSize = GlobalConfig.getDefaultPageLimit
        if (pageSize > appProperties.getMaxPageSize) pageSize = appProperties.getMaxPageSize
        dto.page = pageSize
        this.projectFilesDao.pageQueryAllProjectFiles(dto)
    }

    def mkdir(dto: ProjectFileDto): Unit = {
        val dir = ProjectFileAdapter.createDirFile(dto)
    }

}
