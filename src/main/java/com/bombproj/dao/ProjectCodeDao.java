package com.bombproj.dao;

import com.bombproj.constants.ProjectCodeState;
import com.bombproj.dto.ProjectCodeDto;
import com.bombproj.model.ProjectCode;
import com.bombproj.utils.Utils;
import com.bombproj.vo.ProjectCodeVO;
import com.queryflow.accessor.A;
import com.queryflow.page.Pager;
import com.queryflow.sql.SqlBox;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class ProjectCodeDao {

    public Pager<ProjectCodeVO> pageQueryProjectCodes(ProjectCodeDto dto) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT id, code, message, createTime FROM project_code WHERE state = ? AND projectId = ? ");
        values.add(ProjectCodeState.COMMON.getState());
        values.add(dto.getProjectId());
        if(Utils.isNotEmpty(dto.getCode())) {
            sql.append(" AND code LIKE ? ");
            values.add("%" + dto.getCode() + "%");
        }
        if(Utils.isNotEmpty(dto.getMessage())) {
            sql.append(" AND message LIKE ? ");
            values.add("%" + dto.getMessage() + "%");
        }
        sql.append(" ORDER BY createTime DESC ");
        return A.page(sql.toString(), values, dto.getPage(), ProjectCodeVO.class);
    }

    public Integer countProjectCodeByCode(String code, String projectId) {
        String sql = "SELECT COUNT(id) FROM project_code WHERE code = ? AND projectId = ?";
        return A.query(sql, code, projectId).one(Integer.class);
    }

    public void insertProjectCode(ProjectCode projectCode) {
        SqlBox.insert(projectCode);
    }

}
