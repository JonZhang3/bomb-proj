package com.bombproj.service;

import com.bombproj.constants.DbType;
import com.bombproj.constants.ProjectDataTableState;
import com.bombproj.dao.ProjectDataTableDao;
import com.bombproj.dto.ProjectDataTableDto;
import com.bombproj.framework.aop.Transaction;
import com.bombproj.framework.exception.BusinessException;
import com.bombproj.model.DataTableField;
import com.bombproj.model.DataTableIndexes;
import com.bombproj.model.ProjectDataTable;
import com.bombproj.utils.Utils;
import com.bombproj.vo.DataTableFieldIndexesVO;
import com.bombproj.vo.ProjectDataTableVO;
import com.google.gson.reflect.TypeToken;
import com.queryflow.key.KeyGenerateUtil;
import com.queryflow.page.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectDataTableService {

    @Autowired
    private ProjectDataTableDao projectDataTableDao;

    public List<String> getFieldTypes(String dbType) {
        if(Utils.isEmpty(dbType)) {
            throw new BusinessException("数据库类型未知");
        }
        dbType = dbType.toLowerCase();
        if(!DbType.isOneOf(dbType)) {
            throw new BusinessException("未知数据库类型");
        }
        return this.projectDataTableDao.queryFieldTypesByDbType(dbType);
    }

    public List<DataTableFieldIndexesVO> getFieldIndexes(String dbType) {
        if(Utils.isEmpty(dbType)) {
            throw new BusinessException("数据库类型未知");
        }
        dbType = dbType.toLowerCase();
        if(!DbType.isOneOf(dbType)) {
            throw new BusinessException("未知数据库类型");
        }
        List<DataTableIndexes> indexes = this.projectDataTableDao.queryFieldIndexesByDbType(dbType);
        List<DataTableFieldIndexesVO> result = new LinkedList<>();
        if(indexes != null && !indexes.isEmpty()) {
            for (DataTableIndexes index : indexes) {
                DataTableFieldIndexesVO type = new DataTableFieldIndexesVO();
                type.setValue(index.getType());
                type.setLabel(index.getType());
                List<DataTableFieldIndexesVO> sorts = null;
                if(Utils.isNotEmpty(index.getSort())) {
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
                if(Utils.isNotEmpty(index.getMethod())) {
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
                if(methods == null) {
                    type.setChildren(sorts);
                } else {
                    type.setChildren(methods);
                }
                result.add(type);
            }
        }
        return result;
    }

    public List<ProjectDataTableVO> pageListTables(String tableName, String projectId, Integer page) {
        if(page == null || page <= 0) {
            page = 1;
        }
        return this.projectDataTableDao.pageQueryTables(tableName, projectId, page);
    }

    public void addTable(ProjectDataTableDto dto) {
        if(this.projectDataTableDao.countTableByTableName(dto.getTableName(), null, dto.getProjectId()) > 0) {
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
        this.projectDataTableDao.insertTable(table);
    }

    public void updateTable(ProjectDataTableDto dto) {
        if(this.projectDataTableDao.countTableByTableName(dto.getTableName(),
            dto.getTableId(), dto.getProjectId()) > 0) {
            throw new BusinessException("表名重复");
        }
        this.projectDataTableDao.updateTable(dto);
    }

    @Transaction
    public void deleteTable(ProjectDataTableDto dto) {
        this.projectDataTableDao.deleteTable(dto.getTableId(), dto.getProjectId());
        this.projectDataTableDao.deleteTableAllFields(dto.getTableId());
        //TODO log
    }

    public Map<String, Object> getTableFields(String tableId, String projectId, String version) {
        List<String> versions = this.projectDataTableDao.queryTableFieldVersions(tableId, projectId);
        List<Object> fields = this.projectDataTableDao.queryTableFields(tableId, projectId, version);
        Map<String, Object> result = new HashMap<>();
        result.put("fields", fields);
        result.put("versions", versions);
        return result;
    }

    @Transaction
    public void addOrUpdateTableFields(String tableId, String projectId, String fieldJson) {
        List<DataTableField> fields = Utils.fromJson(fieldJson, new TypeToken<List<DataTableField>>(){}.getType());
        String nowMaxVersion = this.projectDataTableDao.queryTableFieldMaxVersion(tableId, projectId);
        if(Utils.isEmpty(nowMaxVersion) && fields.size() > 0) {
            String version = "1";
            List<List<Object>> result = new LinkedList<>();
            for (DataTableField field : fields) {
                
            }
        }
    }

}
