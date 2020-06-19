package com.bombproj.dao;

import com.bombproj.constants.ProjectState;
import com.bombproj.dto.ProjectDto;
import com.bombproj.model.Project;
import com.bombproj.utils.Utils;
import com.queryflow.accessor.A;
import com.queryflow.page.Pager;
import com.queryflow.sql.SqlBox;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProjectDao {

    public Pager<Project> pageQueryProjects(ProjectDto query) {
        List<Object> values = new LinkedList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT p.id, p.projectName, p.projectDesc, p.createTime, p.gitAddr, ");
        sql.append(" p.cover, p.uniKey, p.type,p.userId,p.userName FROM project p ");
        sql.append(" WHERE p.state = ? ");
        values.add(ProjectState.COMMON.getState());
        if(Utils.isNotEmpty(query.getName())) {
            sql.append(" AND p.projectName LIKE ? ");
            values.add("%" + query.getName() + "%");
        }
        if(query.isAdmin()) {

        } else {
            sql.append(" AND p.userId = ? ");
            values.add(query.getUserId());
        }
        sql.append(" ORDER BY p.createTime DESC ");
        return A.page(sql.toString(), values, query.getPage(), Project.class);
    }

    /**
     * 查询改用户下是否有相同名称的项目
     * @param projectName 项目名称
     * @param createUserId 创建用户 ID
     * @return 返回指定名称项目的数量
     */
    public int countProjectByName(String projectName, String createUserId) {
        String sql = "SELECT COUNT(id) FROM project WHERE projectName = ? AND userId = ?";
        return A.query(sql, projectName, createUserId).one(int.class);
    }

    public void insertProject(Project project) {
        SqlBox.insert(project);
    }

    public void deleteProject(String projectId, String userId) {
        String sql = "UPDATE project SET state = ? WHERE userId = ? AND id = ?";
        A.update(sql, ProjectState.DELETED.getState(), userId, projectId);
    }

    public void updateProject(ProjectDto dto) {
        SqlBox.update("project").set("projectName", dto.getName())
            .set("projectDesc", dto.getDesc())
            .set("type", dto.getType())
            .set("cover", dto.getCover())
            .set("gitAddr", dto.getGitAddr())
            .set("updateTime", new Date())
            .where().eq("id", dto.getId())
            .and().eq("userId", dto.getUserId())
            .execute();
    }

}
