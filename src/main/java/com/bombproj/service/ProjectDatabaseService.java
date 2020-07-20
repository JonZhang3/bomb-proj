package com.bombproj.service;

import com.bombproj.constants.DbType;
import com.bombproj.constants.ProjectDataTableState;
import com.bombproj.constants.ProjectDatabaseState;
import com.bombproj.dao.ProjectDatabaseDao;
import com.bombproj.dto.ProjectDataTableDto;
import com.bombproj.dto.ProjectDatabaseDto;
import com.bombproj.framework.aop.Transaction;
import com.bombproj.framework.exception.BusinessException;
import com.bombproj.model.DataTableField;
import com.bombproj.model.DataTableIndexes;
import com.bombproj.model.ProjectDataTable;
import com.bombproj.model.ProjectDatabase;
import com.bombproj.utils.Utils;
import com.bombproj.vo.DataTableFieldIndexesVO;
import com.bombproj.vo.DataTableFieldVO;
import com.bombproj.vo.ProjectDataTableVO;
import com.bombproj.vo.ProjectDatabaseVO;
import com.google.gson.reflect.TypeToken;
import com.queryflow.key.KeyGenerateUtil;
import com.queryflow.sql.SqlBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectDatabaseService {

    @Autowired
    private ProjectDatabaseDao projectDatabaseDao;

    public List<String> getFieldTypes(String dbType) {
        if (Utils.isEmpty(dbType)) {
            throw new BusinessException("数据库类型未知");
        }
        dbType = dbType.toLowerCase();
        if (!DbType.isOneOf(dbType)) {
            throw new BusinessException("未知数据库类型");
        }
        return this.projectDatabaseDao.queryFieldTypesByDbType(dbType);
    }

    public List<DataTableFieldIndexesVO> getFieldIndexes(String dbType) {
        if (Utils.isEmpty(dbType)) {
            throw new BusinessException("数据库类型未知");
        }
        dbType = dbType.toLowerCase();
        if (!DbType.isOneOf(dbType)) {
            throw new BusinessException("未知数据库类型");
        }
        List<DataTableIndexes> indexes = this.projectDatabaseDao.queryFieldIndexesByDbType(dbType);
        List<DataTableFieldIndexesVO> result = new LinkedList<>();
        if (indexes != null && !indexes.isEmpty()) {
            for (DataTableIndexes index : indexes) {
                DataTableFieldIndexesVO type = new DataTableFieldIndexesVO();
                type.setValue(index.getType());
                type.setLabel(index.getType());
                List<DataTableFieldIndexesVO> sorts = null;
                if (Utils.isNotEmpty(index.getSort())) {
                    String[] sortArr = index.getSort().split(",");
                    sorts = new ArrayList<>(sortArr.length);
                    for (String str : sortArr) {
                        DataTableFieldIndexesVO sort = new DataTableFieldIndexesVO();
                        sort.setValue(str);
                        sort.setLabel(str);
                        sorts.add(sort);
                    }
                }
                List<DataTableFieldIndexesVO> methods = null;
                if (Utils.isNotEmpty(index.getMethod())) {
                    String[] methodArr = index.getMethod().split(",");
                    methods = new ArrayList<>(methodArr.length);
                    for (String str : methodArr) {
                        DataTableFieldIndexesVO method = new DataTableFieldIndexesVO();
                        method.setLabel(str);
                        method.setValue(str);
                        method.setChildren(sorts);
                        methods.add(method);
                    }
                }
                if (methods == null) {
                    type.setChildren(sorts);
                } else {
                    type.setChildren(methods);
                }
                result.add(type);
            }
        }
        return result;
    }

    public List<ProjectDatabaseVO> listDatabases(String projectId, String databaseName) {
        return this.projectDatabaseDao.queryDatabases(projectId, databaseName);
    }

    public void addDatabase(ProjectDatabaseDto dto) {
        if(this.projectDatabaseDao.countDatabaseByName(dto.getDatabaseName(), dto.getProjectId(), null) > 0) {
            throw new BusinessException("数据库名重复");
        }
        ProjectDatabase database = new ProjectDatabase();
        Date now = new Date();
        database.setDatabaseName(dto.getDatabaseName());
        database.setDatabaseDesc(dto.getDatabaseDesc());
        database.setType(dto.getType());
        database.setState(ProjectDatabaseState.COMMON.getState());
        database.setProjectId(dto.getProjectId());
        database.setUserId(dto.getUserId());
        database.setCreateTime(now);
        database.setUpdateTime(now);
        SqlBox.insert(database);
    }

    public void updateDatabase(ProjectDatabaseDto dto) {
        if(this.projectDatabaseDao.countDatabaseByName(dto.getDatabaseName(), dto.getProjectId(), dto.getId()) > 0) {
            throw new BusinessException("数据库名重复");
        }
        this.projectDatabaseDao.updateDatabase(dto);
    }

    @Transaction
    public void deleteDatabase(ProjectDatabaseDto dto) {
        // 1. 删除数据库
        this.projectDatabaseDao.deleteDatabase(dto.getProjectId(), dto.getId());
        // 2. 删除数据库表
        this.projectDatabaseDao.deleteTablesByDatabaseId(dto.getProjectId(), dto.getId());
        // 3. 删除列信息
        this.projectDatabaseDao.deleteTableFieldsByDatabaseId(dto.getProjectId(), dto.getId());
        // TODO log
    }

    public List<ProjectDataTableVO> pageListTables(String tableName, String dbId, String projectId, Integer page) {
        if (page == null || page <= 0) {
            page = 1;
        }
        return this.projectDatabaseDao.pageQueryTables(tableName, dbId, projectId, page);
    }

    public void addTable(ProjectDataTableDto dto) {
        if (this.projectDatabaseDao.countTableByTableName(dto.getTableName(),
            null, dto.getProjectId(), dto.getDatabaseId()) > 0) {
            throw new BusinessException("表名重复");
        }
        ProjectDataTable table = new ProjectDataTable();
        Date now = new Date();
        table.setId(KeyGenerateUtil.generateId() + "");
        table.setTableName(dto.getTableName());
        table.setTableDesc(dto.getTableDesc());
        table.setCreateTime(now);
        table.setUpdateTime(now);
        table.setType(dto.getType());
        table.setState(ProjectDataTableState.COMMON.getState());
        table.setProjectId(dto.getProjectId());
        table.setUserId(dto.getUserId());
        table.setDatabaseId(dto.getDatabaseId());
        this.projectDatabaseDao.insertTable(table);
    }

    public void updateTable(ProjectDataTableDto dto) {
        if (this.projectDatabaseDao.countTableByTableName(dto.getTableName(),
            dto.getTableId(), dto.getProjectId(), dto.getDatabaseId()) > 0) {
            throw new BusinessException("表名重复");
        }
        this.projectDatabaseDao.updateTable(dto);
    }

    @Transaction
    public void deleteTable(ProjectDataTableDto dto) {
        this.projectDatabaseDao.deleteTable(dto.getTableId(), dto.getProjectId());
        this.projectDatabaseDao.deleteTableAllFields(dto.getTableId(), dto.getProjectId());
        //TODO log
    }

    public Map<String, Object> getTableFields(String tableId, String projectId, String version) {
        List<String> versions = this.projectDatabaseDao.queryTableFieldVersions(tableId, projectId);
        List<DataTableFieldVO> fields = this.projectDatabaseDao.queryTableFields(tableId, projectId, version);
        Map<String, Object> result = new HashMap<>();
        result.put("fields", fields);
        result.put("versions", versions);
        return result;
    }

    @Transaction
    public void addOrUpdateTableFields(String tableId, String projectId, String userId, Long dbId, String fieldJson) {
        List<DataTableField> fields = Utils.fromJson(fieldJson, new TypeToken<List<DataTableField>>() {}.getType());
        String nowMaxVersion = this.projectDatabaseDao.queryTableFieldMaxVersion(tableId, projectId);
        String version;
        if (Utils.isEmpty(nowMaxVersion)) {
            version = "1";
        } else {
            version = Integer.parseInt(nowMaxVersion) + 1 + "";
        }
        if (fields != null && fields.size() > 0) {
            this.projectDatabaseDao.batchInsertTableFields(fields, projectId, tableId, dbId, version, userId);
        }
    }

}
