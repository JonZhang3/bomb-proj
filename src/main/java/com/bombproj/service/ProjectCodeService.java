package com.bombproj.service;

import com.bombproj.adapter.ProjectCodeAdapter;
import com.bombproj.dao.ProjectCodeDao;
import com.bombproj.dto.ProjectCodeDto;
import com.bombproj.framework.BusinessException;
import com.bombproj.utils.Utils;
import com.bombproj.vo.ProjectCodeVO;
import com.queryflow.page.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectCodeService {

    @Autowired
    private ProjectCodeDao projectCodeDao;

    public Pager<ProjectCodeVO> pageListProjectCodes(ProjectCodeDto dto) {
        Integer page = dto.getPage();
        if(page == null || page <= 0) {
            page = 1;
        }
        dto.setPage(page);
        return this.projectCodeDao.pageQueryProjectCodes(dto);
    }

    public void addProjectCode(ProjectCodeDto dto) {
        if(this.projectCodeDao.countProjectCodeByCode(dto.getCode(), dto.getProjectId()) > 0) {
            throw new BusinessException("已存在相同的状态码");
        }
        this.projectCodeDao.insertProjectCode(ProjectCodeAdapter.getModel(dto));
    }

    public void updateProjectCode(ProjectCodeDto dto) {
        if(Utils.isEmpty(dto.getId())) {
            throw new BusinessException("修改失败");
        }
        if(this.projectCodeDao.countProjectCodeByCode(dto.getCode(), dto.getProjectId()) > 0) {
            throw new BusinessException("已存在相同的状态码");
        }
        this.projectCodeDao.updateProjectId(dto);
    }

    public void deleteProjectCode(ProjectCodeDto dto) {
        this.projectCodeDao.deleteProjectId(dto.getId(), dto.getProjectId());
    }

}
