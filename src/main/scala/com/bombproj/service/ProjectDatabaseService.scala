package com.bombproj.service

import java.util
import java.util.Date

import com.bombproj.constants.{DbType, ProjectDataTableState, ProjectDatabaseState}
import com.bombproj.dao.ProjectDatabaseDao
import com.bombproj.dto.{ProjectDataTableDto, ProjectDatabaseDto}
import com.bombproj.framework.aop.Transaction
import com.bombproj.framework.exception.BusinessException
import com.bombproj.model._
import com.bombproj.utils.Utils
import com.bombproj.vo._
import com.google.gson.reflect.TypeToken
import com.queryflow.key.KeyGenerateUtil
import com.queryflow.page.Pager
import com.queryflow.sql.SqlBox
import javax.annotation.Resource
import org.springframework.stereotype.Service

@Service
class ProjectDatabaseService @Resource() (projectDatabaseDao: ProjectDatabaseDao) {

    def getFieldTypes(dbType: String): util.List[String] = {
        if (Utils.isEmpty(dbType)) throw new BusinessException(message = "数据库类型不能为空")
        val _dbType = dbType.toLowerCase
        if (!DbType.isOneOf(_dbType)) throw new BusinessException(message = "未知数据库类型")
        this.projectDatabaseDao.queryFieldTypesByDbType(_dbType)
    }

    def getFieldIndexes(dbType: String): util.List[DataTableFieldIndexesVO] = {
        if (Utils.isEmpty(dbType)) throw new BusinessException(message = "数据库类型不能为空")
        val _dbType = dbType.toLowerCase
        if (!DbType.isOneOf(_dbType)) throw new BusinessException(message = "未知数据库类型")
        val indexes: util.List[DataTableIndexes] = this.projectDatabaseDao.queryFieldIndexesByDbType(dbType)
        val result: util.List[DataTableFieldIndexesVO] = new util.LinkedList[DataTableFieldIndexesVO]
        if (indexes != null && !indexes.isEmpty) {
            indexes.forEach(index => {
                val fieldIndex: DataTableFieldIndexesVO = new DataTableFieldIndexesVO
                fieldIndex.setValue(index.`type`)
                fieldIndex.setLabel(index.`type`)
                var sorts: util.List[DataTableFieldIndexesVO] = null
                if(Utils.isNotEmpty(index.sort)) {
                    val sortArr = index.sort.split(",")
                    sorts = new util.ArrayList[DataTableFieldIndexesVO](sortArr.length)
                    for(str <- sortArr) {
                        val sort: DataTableFieldIndexesVO = new DataTableFieldIndexesVO
                        sort.value = str
                        sort.label = str
                        sorts.add(sort)
                    }
                }
                var methods: util.List[DataTableFieldIndexesVO] = null
                if(Utils.isNotEmpty(index.method)) {
                    val methodArr = index.method.split(",")
                    methods = new util.ArrayList[DataTableFieldIndexesVO](methodArr.length)
                    for (str <- methodArr) {
                        val method: DataTableFieldIndexesVO = new DataTableFieldIndexesVO
                        method.label = str
                        method.value = str
                        method.children = sorts
                        methods.add(method)
                    }
                }
                if (methods == null) fieldIndex.children = sorts
                else fieldIndex.children = methods
                result.add(fieldIndex)
            })
        }
        result
    }

    def listDatabases(projectId: String, databaseName: String): util.List[ProjectDatabaseVO] =
        this.projectDatabaseDao.queryDatabases(projectId, databaseName)

    def addDatabase(dto: ProjectDatabaseDto): Unit = {
        if (this.projectDatabaseDao.countDatabaseByName(dto.getDatabaseName, dto.getProjectId, null) > 0)
            throw new BusinessException(message = "数据库名重复")
        val database: ProjectDatabase = new ProjectDatabase
        val now: Date = new Date
        database.databaseName = dto.databaseName
        database.databaseDesc = dto.databaseDesc
        database.dbHost = dto.host
        database.dbPort = dto.port + ""
        database.userName = dto.userName
        database.`type` = dto.`type`
        database.version = dto.version
        database.state = ProjectDatabaseState.COMMON.getState
        database.projectId = dto.projectId
        database.userId = dto.userId
        database.createTime = now
        database.updateTime = now
        SqlBox.insert(database)
    }

    def updateDatabase(dto: ProjectDatabaseDto): Unit = {
        if (this.projectDatabaseDao.countDatabaseByName(dto.getDatabaseName, dto.getProjectId, dto.getId) > 0)
            throw new BusinessException(message = "数据库名重复")
        this.projectDatabaseDao.updateDatabase(dto)
    }

    @Transaction
    def deleteDatabase(dto: ProjectDatabaseDto): Unit = { // 1. 删除数据库
        this.projectDatabaseDao.deleteDatabase(dto.projectId, dto.id)
        // 2. 删除数据库表
        this.projectDatabaseDao.deleteTablesByDatabaseId(dto.projectId, dto.id)
        // 3. 删除列信息
        this.projectDatabaseDao.deleteTableFieldsByDatabaseId(dto.projectId, dto.id)
        // 4. 删除索引信息
        this.projectDatabaseDao.deleteTableIndexesByDatabaseId(dto.projectId, dto.id)
        // TODO log
    }

    def pageListTables(tableName: String, dbId: String, projectId: String, page: Int): Pager[ProjectDataTableVO] = {
        var _page = page
        if (_page <= 0) _page = 1
        this.projectDatabaseDao.pageQueryTables(tableName, dbId, projectId, _page)
    }

    def addTable(dto: ProjectDataTableDto): String = {
        if (this.projectDatabaseDao.countTableByTableName(dto.tableName, null, dto.projectId, dto.databaseId) > 0)
            throw new BusinessException(message = "表名重复")
        val table: ProjectDataTable = new ProjectDataTable
        val now: Date = new Date
        val id: String = KeyGenerateUtil.generateId + ""
        table.id = id
        table.tableName = dto.tableName
        table.tableDesc = dto.tableDesc
        table.createTime = now
        table.updateTime = now
        table.state = ProjectDataTableState.COMMON.getState
        table.projectId = dto.projectId
        table.userId = dto.userId
        table.databaseId = dto.databaseId
        this.projectDatabaseDao.insertTable(table)
        id
    }

    @Transaction
    def updateTable(dto: ProjectDataTableDto): Unit = {
        if (this.projectDatabaseDao.countTableByTableName(dto.tableName, dto.tableId, dto.projectId, dto.databaseId) > 0)
            throw new BusinessException(message = "表名重复")
        this.projectDatabaseDao.updateTable(dto)
        val fields: util.List[DataTableField] = Utils.fromJson(dto.getFields, new TypeToken[util.List[DataTableField]]() {}.getType)
        if (fields != null && fields.size > 0) {
            val nowMaxVersion: String = this.projectDatabaseDao.queryTableFieldMaxVersion(dto.getTableId, dto.getProjectId)
            var version: String = null
            if (Utils.isEmpty(nowMaxVersion)) version = "1"
            else version = nowMaxVersion.toInt + 1 + ""
            this.projectDatabaseDao.batchInsertTableFields(fields, dto.getProjectId, dto.getTableId, dto.getDatabaseId, version, dto.getUserId)
        }
        val indexes: util.List[DataTableIndex] = Utils.fromJson(dto.getIndexes, new TypeToken[util.List[DataTableIndex]]() {}.getType)
        if (indexes != null && indexes.size > 0) {
        }
    }

    @Transaction def deleteTable(dto: ProjectDataTableDto): Unit = { // 1. 删除所有的表
        this.projectDatabaseDao.deleteTable(dto.tableId, dto.projectId)
        // 2. 删除所有的列
        this.projectDatabaseDao.deleteTableAllFields(dto.tableId, dto.projectId)
        // 3. 删除所有的索引
        this.projectDatabaseDao.deleteTableAllIndexes(dto.tableId, dto.projectId)
        //TODO log
    }

    def getTableIndexes(tableId: String, projectId: String, version: String): Map[String, AnyRef] = {
        val versions: util.List[String] = this.projectDatabaseDao.queryTableIndexesVersions(tableId, projectId)
        val indexes: util.List[DataTableIndexesVO] = this.projectDatabaseDao.queryTableIndexes(tableId, projectId, version)
        val result = Map("indexes" -> indexes, "versions" -> versions)
        result
    }

    def getTableFields(tableId: String, projectId: String, version: String): Map[String, AnyRef] = {
        val versions: util.List[String] = this.projectDatabaseDao.queryTableFieldVersions(tableId, projectId)
        val fields: util.List[DataTableFieldVO] = this.projectDatabaseDao.queryTableFields(tableId, projectId, version)
        val result = Map("fields" -> fields, "versions" -> versions)
        result
    }

    @Transaction def addOrUpdateTableFields(tableId: String, projectId: String, userId: String, dbId: Long, fieldJson: String): Unit = {
        val fields: util.List[DataTableField] = Utils.fromJson(fieldJson, new TypeToken[util.List[DataTableField]]() {}.getType)
        if (fields != null && fields.size > 0) {
            val nowMaxVersion: String = this.projectDatabaseDao.queryTableFieldMaxVersion(tableId, projectId)
            var version: String = null
            if (Utils.isEmpty(nowMaxVersion)) version = "1"
            else version = nowMaxVersion.toInt + 1 + ""
            this.projectDatabaseDao.batchInsertTableFields(fields, projectId, tableId, dbId, version, userId)
        }
    }

    @Transaction def updateTableAndFieldsAndIndexes(tableId: String, projectId: String): Unit = {
    }

}
