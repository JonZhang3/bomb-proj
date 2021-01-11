package com.bombproj.service

import com.bombproj.adapter.ProjectCodeAdapter
import com.bombproj.dao.ProjectCodeDao
import com.bombproj.dto.ProjectCodeDto
import com.bombproj.framework.exception.BusinessException
import com.bombproj.utils.Utils
import com.bombproj.vo.ProjectCodeVO
import com.queryflow.page.Pager
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class ProjectCodeService @Resource() (projectCodeDao: ProjectCodeDao) {

    def pageListProjectCodes(dto: ProjectCodeDto): Pager[ProjectCodeVO] = {
        var page = dto.page
        if (page <= 0) page = 1
        dto.page = page
        this.projectCodeDao.pageQueryProjectCodes(dto)
    }

    def addProjectCode(dto: ProjectCodeDto): Unit = {
        if (this.projectCodeDao.countProjectCodeByCode(dto.code, dto.projectId) > 0)
            throw new BusinessException(message = "已存在相同的状态码")
        this.projectCodeDao.insertProjectCode(ProjectCodeAdapter.getModel(dto))
    }

    def updateProjectCode(dto: ProjectCodeDto): Unit = {
        if (Utils.isEmpty(dto.getId)) throw new BusinessException(message = "修改失败")
        if (this.projectCodeDao.countProjectCodeByCode(dto.code, dto.projectId) > 0)
            throw new BusinessException(message = "已存在相同的状态码")
        this.projectCodeDao.updateProjectId(dto)
    }

    def deleteProjectCode(dto: ProjectCodeDto): Unit = {
        this.projectCodeDao.deleteProjectId(dto.id, dto.projectId)
    }

}
