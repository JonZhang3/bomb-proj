package com.bombproj.dao;

import com.bombproj.adapter.DataTableAdapter;
import com.bombproj.constants.DataTableFieldState;
import com.bombproj.constants.ProjectDataTableState;
import com.bombproj.constants.ProjectDatabaseState;
import com.bombproj.constants.ProjectState;
import com.bombproj.dto.ProjectDataTableDto;
import com.bombproj.dto.ProjectDatabaseDto;
import com.bombproj.model.DataTableField;
import com.bombproj.model.DataTableIndexes;
import com.bombproj.model.ProjectDataTable;
import com.bombproj.utils.Utils;
import com.bombproj.vo.DataTableFieldVO;
import com.bombproj.vo.ProjectDataTableVO;
import com.bombproj.vo.ProjectDatabaseVO;
import com.queryflow.accessor.A;
import com.queryflow.sql.SqlBox;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProjectDatabaseDao {

    public List<String> queryFieldTypesByDbType(String dbType) {
        String sql = "SELECT type FROM datatable_type WHERE dbType = ? ORDER BY type";
        return A.query(sql, dbType).list(String.class);
    }

    public List<DataTableIndexes> queryFieldIndexesByDbType(String dbType) {
        String sql = "SELECT type, method, sort FROM datatable_indexes WHERE dbType = ? ORDER BY sequence";
        return A.query(sql, dbType).list(DataTableIndexes.class);
    }

    /**
     * 查询指定项目的数据库列表
     * @param projectId 项目 ID
     * @param databaseName 模糊查询条件：数据库名
     * @return 数据库列表
     */
    public List<ProjectDatabaseVO> queryDatabases(String projectId, String databaseName) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT d.id, d.databaseName, d.databaseDesc, d.type, d.updateTime, d.createTime, u.nickName createUser ");
        sql.append(" FROM project_database d ");
        sql.append(" JOIN project p ON d.projectId = p.id JOIN users u ON d.userId = u.id ");
        sql.append(" WHERE d.state = ? AND p.state = ? AND d.projectId = ? ");
        values.add(ProjectDatabaseState.COMMON.getState());
        values.add(ProjectState.COMMON.getState());
        values.add(projectId);
        if(Utils.isNotEmpty(databaseName)) {
            sql.append(" AND d.databaseName LIKE ? ");
            values.add("%" + databaseName + "%");
        }
        sql.append(" ORDER BY updateTime DESC ");
        return A.query(sql.toString(), values).list(ProjectDatabaseVO.class);
    }

    public Integer countDatabaseByName(String databaseName, String projectId, String databaseId) {
        String sql = "SELECT COUNT(id) FROM project_database WHERE projectId = ? AND state = ? AND databaseName = ?";
        List<Object> values = new LinkedList<>();
        values.add(projectId);
        values.add(ProjectDatabaseState.COMMON.getState());
        values.add(databaseName);
        if(Utils.isNotEmpty(databaseId)) {
            sql += " AND id <> ? ";
            values.add(databaseId);
        }
        return A.query(sql, values).one(Integer.class);
    }

    public void updateDatabase(ProjectDatabaseDto dto) {
        SqlBox.update("project_database")
            .set("databaseName", dto.getDatabaseName())
            .set("databaseDesc", dto.getDatabaseDesc())
            .set("type", dto.getType())
            .set("updateTime", new Date())
            .where().eq("id", dto.getId())
            .and().eq("state", ProjectDatabaseState.COMMON.getState())
            .execute();
    }

    public void deleteDatabase(String projectId, String dbId) {
        SqlBox.update("project_database")
            .set("state", ProjectDatabaseState.DELETED.getState())
            .set("updateTime", new Date())
            .where().eq("projectId", projectId)
            .and().eq("id", dbId)
            .execute();
    }

    public void deleteTablesByDatabaseId(String projectId, String dbId) {
        SqlBox.update("project_datatable")
            .set("state", ProjectDataTableState.DELETED.getState())
            .set("updateTime", new Date())
            .where().eq("projectId", projectId)
            .and().eq("databaseId", dbId)
            .execute();
    }

    public void deleteTableFieldsByDatabaseId(String projectId, String dbId) {
        SqlBox.update("datatable_field")
            .set("state", DataTableFieldState.DELETED.getState())
            .set("updateTime", new Date())
            .where().eq("projectId", projectId)
            .and().eq("databaseId", dbId)
            .execute();
    }

    public List<ProjectDataTableVO> pageQueryTables(String tableName, String dbId, String projectId, Integer page) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT d.id,d.tableName,d.tableDesc,d.type,d.updateTime,u.nickName createUser FROM project_datatable d ");
        sql.append(" JOIN project p ON d.projectId = p.id JOIN users u ON d.userId = u.id ");
        sql.append(" WHERE d.state = ? AND p.state = ? AND d.projectId = ? AND databaseId = ? ");
        values.add(ProjectDataTableState.COMMON.getState());
        values.add(ProjectState.COMMON.getState());
        values.add(projectId);
        values.add(dbId);
        if(Utils.isNotEmpty(tableName)) {
            sql.append(" AND d.tableName LIKE ? ");
            values.add("%" + tableName + "%");
        }
        sql.append(" ORDER BY d.tableName ");
        return A.query(sql.toString(), values).list(ProjectDataTableVO.class);
    }

    public Integer countTableByTableName(String tableName, String tableId, String projectId, Long dbId) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT COUNT(id) FROM project_datatable WHERE projectId = ? AND state = ? AND tableName = ? ");
        sql.append(" AND databaseId = ? ");
        values.add(projectId);
        values.add(ProjectDataTableState.COMMON.getState());
        values.add(tableName);
        values.add(dbId);
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
            .where().eq("projectId", dto.getProjectId())
            .and().eq("databaseId", dto.getDatabaseId())
            .and().eq("id", dto.getTableId())
            .execute();
    }

    public void deleteTable(String tableId, String projectId) {
        SqlBox.update("project_datatable")
            .set("state", ProjectDataTableState.DELETED.getState())
            .set("updateTime", new Date())
            .where().eq("projectId", projectId)
            .and().eq("id", tableId)
            .execute();
    }

    public void deleteTableAllFields(String tableId, String projectId) {
        SqlBox.update("datatable_field")
            .set("state", DataTableFieldState.DELETED.getState())
            .set("updateTime", new Date())
            .where().eq("projectId", projectId)
            .and().eq("datatableId", tableId)
            .execute();
    }

    public String queryTableFieldMaxVersion(String tableId, String projectId) {
        String sql = "SELECT MAX(version) FROM datatable_field WHERE projectId = ? AND datatableId = ?";
        return A.query(sql, projectId, tableId).one(String.class);
    }

    public List<String> queryTableFieldVersions(String tableId, String projectId) {
        String sql = "SELECT version FROM datatable_field WHERE projectId = ? AND datatableId = ? GROUP BY version" +
            " ORDER BY version DESC";
        return A.query(sql, projectId, tableId).list(String.class);
    }

    public List<DataTableFieldVO> queryTableFields(String tableId, String projectId, String version) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT f.id, f.fieldName, f.type, f.length, f.notNull, f.pk, f.autoIncrement, ");
        sql.append(" f.defaultValue, f.notes, f.indexes, f.indexesName, f.state, f.marker, f.version, u.nickName createUserName ");
        sql.append(" FROM datatable_field f LEFT JOIN users u ON f.userId = u.id ");
        sql.append(" WHERE f.projectId = ? AND f.datatableId = ? AND f.state = ? ");
        values.add(projectId);
        values.add(tableId);
        values.add(DataTableFieldState.COMMON.getState());
        if(Utils.isEmpty(version)) {
            sql.append(" AND f.version = (SELECT MAX(version) FROM datatable_field WHERE projectId = ? AND datatableId = ?) ");
            values.add(projectId);
            values.add(tableId);
        } else {
            sql.append(" AND f.version = ? ");
            values.add(version);
        }
        sql.append(" ORDER BY f.sequence ");
        return A.query(sql.toString(), values).result(rs -> {
            List<DataTableFieldVO> result = new LinkedList<>();
            while (rs.next()) {
                DataTableFieldVO vo = new DataTableFieldVO();
                vo.setId(rs.getString("id"));
                vo.setFieldName(rs.getString("fieldName"));
                vo.setType(rs.getString("type"));
                vo.setLength(rs.getString("length"));
                vo.setNotNull(rs.getString("notNull"));
                vo.setPk(rs.getString("pk"));
                vo.setAutoIncrement(rs.getString("autoIncrement"));
                vo.setDefaultValue(rs.getString("defaultValue"));
                vo.setNotes(rs.getString("notes"));
                String indexes = rs.getString("indexes");
                String[] indexesArr = null;
                if(Utils.isNotEmpty(indexes)) {
                    indexesArr = indexes.split(",");
                }
                vo.setIndexes(indexesArr);
                vo.setIndexesName(rs.getString("indexesName"));
                vo.setState(rs.getInt("state"));
                vo.setMarker(rs.getString("marker"));
                vo.setVersion(rs.getString("version"));
                vo.setCreateUserName(rs.getString("createUserName"));
                result.add(vo);
            }
            return result;
        });
    }

    public void batchInsertTableFields(List<DataTableField> fields, String projectId, String datatableId, Long dbId,
                                       String version, String userId) {
        String sql = "INSERT INTO datatable_field (id, fieldName, type, length, " +
            "notNull, pk, autoIncrement, defaultValue, notes, indexes, indexesName, state, marker, " +
            "version, datatableId, projectId, userId, createTime, updateTime, sequence, databaseId) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        A.batch(sql, DataTableAdapter.getInsertListValues(fields, projectId, datatableId, dbId, version, userId));
    }

    public void batchUpdateTableFields() {

    }

}
