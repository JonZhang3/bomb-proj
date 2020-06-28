package com.bombproj.dao;

import com.bombproj.constants.DataTableFieldState;
import com.bombproj.constants.ProjectDataTableState;
import com.bombproj.constants.ProjectState;
import com.bombproj.dto.ProjectDataTableDto;
import com.bombproj.model.DataTableIndexes;
import com.bombproj.model.ProjectDataTable;
import com.bombproj.utils.Utils;
import com.bombproj.vo.ProjectDataTableVO;
import com.queryflow.accessor.A;
import com.queryflow.page.Pager;
import com.queryflow.sql.SqlBox;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProjectDataTableDao {

    public List<String> queryFieldTypesByDbType(String dbType) {
        String sql = "SELECT type FROM datatable_type WHERE dbType = ? ORDER BY type";
        return A.query(sql, dbType).list(String.class);
    }

    public List<DataTableIndexes> queryFieldIndexesByDbType(String dbType) {
        String sql = "SELECT type, method, sort FROM datatable_indexes WHERE dbType = ? ORDER BY sequence";
        return A.query(sql, dbType).list(DataTableIndexes.class);
    }

    public List<ProjectDataTableVO> pageQueryTables(String tableName, String projectId, Integer page) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT d.id,d.tableName,d.tableDesc,d.type,d.updateTime,u.nickName createUser FROM project_datatable d ");
        sql.append(" JOIN project p ON d.projectId = p.id JOIN users u ON d.userId = u.id ");
        sql.append(" WHERE d.state = ? AND p.state = ? AND d.projectId = ? ");
        values.add(ProjectDataTableState.COMMON.getState());
        values.add(ProjectState.COMMON.getState());
        values.add(projectId);
        if(Utils.isNotEmpty(tableName)) {
            sql.append(" AND d.tableName LIKE ? ");
            values.add("%" + tableName + "%");
        }
        sql.append(" ORDER BY d.tableName ");
        return A.query(sql.toString(), values).list(ProjectDataTableVO.class);
//        return A.page(sql.toString(), values, page, ProjectDataTableVO.class);
    }

    public Integer countTableByTableName(String tableName, String tableId, String projectId) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT COUNT(id) FROM project_datatable WHERE projectId = ? AND state = ? AND tableName = ? ");
        values.add(projectId);
        values.add(ProjectDataTableState.COMMON.getState());
        values.add(tableName);
        if(Utils.isNotEmpty(tableId)) {
            sql.append(" AND id <> ? ");
            values.add(tableId);
        }
        return A.query(sql.toString(), values).one(Integer.class);
    }

    public void insertTable(ProjectDataTable table) {
        SqlBox.insert(table);
    }

    public void updateTable(ProjectDataTableDto dto) {
        SqlBox.update("project_datatable")
            .set("tableName", dto.getTableName())
            .set("tableDesc", dto.getTableDesc())
            .set("type", dto.getType())
            .set("updateTime", new Date())
            .where().eq("id", dto.getTableId())
            .execute();
    }

    public void deleteTable(String tableId, String projectId) {
        SqlBox.delete("project_datatable")
            .where().eq("id", tableId)
            .and().eq("projectId", projectId)
            .execute();
    }

    public void deleteTableAllFields(String tableId) {
        SqlBox.delete("datatable_field")
            .where().eq("datatableId", tableId)
            .execute();
    }

    public String queryTableFieldMaxVersion(String tableId, String projectId) {
        String sql = "SELECT MAX(version) FROM datatable_field WHERE projectId = ? AND datatableId = ? GROUP BY version";
        return A.query(sql, projectId, tableId).one(String.class);
    }

    public List<String> queryTableFieldVersions(String tableId, String projectId) {
        String sql = "SELECT version FROM datatable_field WHERE projectId = ? AND datatableId = ? GROUP BY version" +
            "ORDER BY version DESC";
        return A.query(sql, projectId, tableId).list(String.class);
    }

    public List<Object> queryTableFields(String tableId, String projectId, String version) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT f.id, f.fieldName, f.type, f.length, f.notNull, ");
        sql.append(" f.defaultValue, f.notes, f.indexes, f.indexesName, f.state, f.marker, f.version, u.nickName createUserName ");
        sql.append(" FROM datatable_field f LEFT JOIN users u ON f.userId = u.id ");
        sql.append(" WHERE f.projectId = ? AND f.datatableId = ? ");
        values.add(projectId);
        values.add(tableId);
        if(Utils.isEmpty(version)) {
            sql.append(" AND f.version = (SELECT MAX(version) FROM datatable_field WHERE projectId = ? AND datatableId = ?) ");
            values.add(projectId);
            values.add(tableId);
        } else {
            sql.append(" AND f.version = ? ");
            values.add(version);
        }
        sql.append(" ORDER BY f.id ");
        return A.query(sql.toString(), values).list(null);
    }

    public void batchInsertTableFields(List<List<Object>> fields) {
        String sql = "INSERT INTO datatable_field (id, fieldName, type, length, " +
            "notNull, defaultValue, notes, indexes, indexesName, state, marker, " +
            "version, datatableId, projectId, userId, createTime, updateTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        A.batch(sql, fields);
    }



}
