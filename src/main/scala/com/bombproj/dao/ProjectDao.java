package com.bombproj.dao;

import com.bombproj.constants.ProjectMemberState;
import com.bombproj.constants.ProjectMemberType;
import com.bombproj.constants.ProjectState;
import com.bombproj.constants.UserState;
import com.bombproj.dto.ProjectDto;
import com.bombproj.dto.ProjectMemberDto;
import com.bombproj.model.Project;
import com.bombproj.utils.Utils;
import com.bombproj.vo.ProjectMemberListVO;
import com.bombproj.vo.UserQueryResultVO;
import com.queryflow.accessor.A;
import com.queryflow.key.KeyGenerateUtil;
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
        sql.append(" p.cover, p.uniKey, p.type, p.userId, u.nickName userName FROM project p ");
        sql.append(" JOIN users u ON p.userId = u.id ");
        sql.append(" WHERE p.state = ? ");
        values.add(ProjectState.COMMON.getState());
        if (Utils.isNotEmpty(query.getName())) {
            sql.append(" AND p.projectName LIKE ? ");
            values.add("%" + query.getName() + "%");
        }

        if (query.isAdmin()) {

        } else {
            sql.append(" AND p.userId = ? ");
            values.add(query.getUserId());
        }
        sql.append(" ORDER BY p.createTime DESC ");
        return A.page(sql.toString(), values, query.getPage(), Project.class);
    }

    /**
     * 查询改用户下是否有相同名称的项目
     *
     * @param projectName  项目名称
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

    public Project queryProjectById(String projectId, String userId) {
        String sql = "SELECT p.id, p.projectName, p.projectDesc, p.createTime, p.updateTime, " +
            "p.gitAddr,p.cover, p.uniKey, p.type, p.userId, u.nickName userName " +
            "FROM project p JOIN users u ON p.userId=u.id WHERE p.id = ? AND p.userId = ?";
        return A.query(sql, projectId, userId).one(Project.class);
    }

    public Pager<ProjectMemberListVO> pageProjectMemebers(ProjectMemberDto dto) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT m.id, m.userId, m.permission, m.createTime, u.userName, u.nickName, u.email, u.phone ");
        sql.append(" FROM project_member m JOIN project p ON m.projectId = p.id ");
        sql.append(" JOIN users u ON m.userId = u.id ");
        sql.append(" WHERE u.state = ? AND p.userId = ? AND m.state = ? ");
        List<Object> values = new LinkedList<>();
        values.add(UserState.VALID.getCode());
        values.add(dto.getCreateUserId());
        values.add(ProjectMemberState.NORMAL.getState());

        if (Utils.isNotEmpty(dto.getUserName())) {
            sql.append(" AND u.nickName LIKE ? ");
            values.add("%" + dto.getUserName() + "%");
        }
        sql.append(" AND m.projectId = ? ");
        values.add(dto.getProjectId());
        sql.append(" ORDER BY m.createTime DESC ");
        return A.page(sql.toString(), values, dto.getPage(), ProjectMemberListVO.class);
    }

    public List<UserQueryResultVO> queryNotExistsUsers(String name, String projectId) {
        String sqlPrefix = "SELECT u.id, u.userName, u.nickName, u.email, u.phone FROM users u WHERE NOT EXISTS " +
            "(SELECT * FROM project_member m WHERE m.userId = u.id AND m.projectId = ? ) AND u.state = ? ";
        String sql = sqlPrefix + " AND u.userName LIKE ? " +
            " UNION " + sqlPrefix + " AND u.nickName LIKE ? " +
            " UNION " + sqlPrefix + " AND u.email LIKE ? " +
            " UNION " + sqlPrefix + " AND u.phone LIKE ? ";
        return A.query(sql,
            projectId, UserState.VALID.getCode(), "%" + name + "%",
            projectId, UserState.VALID.getCode(), "%" + name + "%",
            projectId, UserState.VALID.getCode(), "%" + name + "%",
            projectId, UserState.VALID.getCode(), "%" + name + "%")
            .list(UserQueryResultVO.class);
    }

    public void batchInsertMember(String projectId, String[] userIds, String permissions, ProjectMemberType memberType) {
        String sql = "INSERT INTO project_member SELECT " +
            "?, ?, ?, ?, ?, ?, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM project_member WHERE " +
            "projectId = ? AND userId = ?)";
        List<List<Object>> values = new LinkedList<>();
        Date now = new Date();
        for (String userId : userIds) {
            if(Utils.isNotEmpty(userId)) {
                List<Object> value = new LinkedList<>();
                value.add(KeyGenerateUtil.generateId() + "");
                value.add(ProjectMemberState.NORMAL.getState());
                value.add("0");
                value.add(now);
                value.add(now);
                value.add(projectId);
                value.add(userId);
                value.add(permissions);
                value.add(memberType.getType());
                value.add(projectId);
                value.add(userId);
                values.add(value);
            }
        }
        A.batch(sql, values);
    }

}
