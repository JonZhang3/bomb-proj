package com.bombproj.dao;

import com.bombproj.dto.ProjectDto;
import com.bombproj.utils.Utils;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class ProjectDao {

    public void pageQueryProjects(ProjectDto query) {
        List<Object> values = new LinkedList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT p.id, p.name, p.createTime, p.cover, p.status ");
        sql.append(" FROM project p LEFT JOIN project_user ON p.id=u.projectId ");
        sql.append(" WHERE u.userId = ? ");
        values.add(query.getUserId());
        if(Utils.isNotEmpty(query.getName())) {
            sql.append(" AND projectName LIKE ? ");
            values.add("%" + query.getName() + "%");
        }
        sql.append(" ORDER BY  ");

    }

}
