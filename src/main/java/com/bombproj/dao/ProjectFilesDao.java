package com.bombproj.dao;

import com.bombproj.constants.ProjectFileState;
import com.bombproj.constants.SortValue;
import com.bombproj.dto.ProjectFileDto;
import com.bombproj.model.ProjectFile;
import com.bombproj.vo.ProjectAllFileVO;
import com.queryflow.accessor.A;
import com.queryflow.page.Pager;
import com.queryflow.sql.SqlBox;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class ProjectFilesDao {

    public Pager<ProjectAllFileVO> pageQueryAllProjectFiles(ProjectFileDto dto) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT f.id, f.fileName, f.suffix, f.size, f.creator, u.nickName creatorName, f.updateTime,f.type, ");
        sql.append(" f.starred,f.shared,f.parentId, f.version, f.fullPath ");
        sql.append(" FROM project_file f LEFT JOIN ");
        sql.append(" (SELECT f2.parentId id,COUNT( f2.id ) childCount FROM project_file f2 WHERE f2.state = ? AND f2.projectId=? ");
        sql.append(" GROUP BY f2.parentId) c ON f.id = c.id ");
        sql.append(" LEFT JOIN users u ON f.creator = u.id ");
        sql.append(" WHERE f.projectId = ? AND f.state = ? AND parentId = ? ");
        values.add(ProjectFileState.NORMAL.getState());
        values.add(dto.getProjectId());
        values.add(dto.getProjectId());
        values.add(ProjectFileState.NORMAL.getState());
        values.add(dto.getFolderId());
        // name、creatorName、updateTime、size
        // desc、asc
        String sortName = dto.getSortName();
        if("creator".equals(sortName)) {
            sql.append(" ORDER BY creatorName ").append(SortValue.ofValue(dto.getSortValue()));
        } else if("updateTime".equals(sortName)) {
            sql.append(" ORDER BY f.updateTime ").append(SortValue.ofValue(dto.getSortValue()));
        } else if("size".equals(sortName)) {
            sql.append(" ORDER BY f.size ").append(SortValue.ofValue(dto.getSortValue()));
        } else {
            sql.append(" ORDER BY f.fileName ").append(SortValue.ofValue(dto.getSortValue()));
        }
        return A.page(sql.toString(), values, dto.getPage(), dto.getPageSize(), ProjectAllFileVO.class);
    }

    public void insertProjectFile(ProjectFile file) {

    }

}
