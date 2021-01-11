package com.bombproj.dao

import java.sql.ResultSet
import java.util
import java.util.Date

import com.bombproj.adapter.DataTableAdapter
import com.bombproj.constants._
import com.bombproj.dto.{ProjectDataTableDto, ProjectDatabaseDto}
import com.bombproj.model.{DataTableField, DataTableIndexes, ProjectDataTable}
import com.bombproj.utils.Utils
import com.bombproj.vo.{DataTableFieldVO, DataTableIndexesVO, ProjectDataTableVO, ProjectDatabaseVO}
import com.queryflow.accessor.A
import com.queryflow.page.Pager
import com.queryflow.sql.SqlBox
import org.springframework.stereotype.Repository

@Repository
class ProjectDatabaseDao {

    def queryFieldTypesByDbType(dbType: String): util.List[String] = {
        val sql = "SELECT type FROM datatable_type WHERE dbType = ? ORDER BY type"
        A.query(sql, dbType).list(classOf[String])
    }

    def queryFieldIndexesByDbType(dbType: String): util.List[DataTableIndexes] = {
        val sql = "SELECT type, method, sort FROM datatable_indexes WHERE dbType = ? ORDER BY sequence"
        A.query(sql, dbType).list(classOf[DataTableIndexes])
    }

    /**
     * 查询指定项目的数据库列表
     *
     * @param projectId    项目 ID
     * @param databaseName 模糊查询条件：数据库名
     * @return 数据库列表
     */
    def queryDatabases(projectId: String, databaseName: String): util.List[ProjectDatabaseVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT d.id, d.databaseName, d.databaseDesc, d.type, d.dbHost, d.dbPort,d.userName, ")
        sql.append(" d.version, d.updateTime, d.createTime, u.nickName createUser ")
        sql.append(" FROM project_database d ")
        sql.append(" JOIN project p ON d.projectId = p.id JOIN users u ON d.userId = u.id ")
        sql.append(" WHERE d.state = ? AND p.state = ? AND d.projectId = ? ")
        values.add(ProjectDatabaseState.COMMON.getState.asInstanceOf[Integer])
        values.add(ProjectState.COMMON.getState.asInstanceOf[Integer])
        values.add(projectId)
        if (Utils.isNotEmpty(databaseName)) {
            sql.append(" AND d.databaseName LIKE ? ")
            values.add(s"%$databaseName%")
        }
        sql.append(" ORDER BY updateTime DESC ")
        A.query(sql.toString, values).list(classOf[ProjectDatabaseVO])
    }

    def countDatabaseByName(databaseName: String, projectId: String, databaseId: String): Integer = {
        var sql = "SELECT COUNT(id) FROM project_database WHERE projectId = ? AND state = ? AND databaseName = ?"
        val values = new util.LinkedList[AnyRef]
        values.add(projectId)
        values.add(ProjectDatabaseState.COMMON.getState.asInstanceOf[Integer])
        values.add(databaseName)
        if (Utils.isNotEmpty(databaseId)) {
            sql += " AND id <> ? "
            values.add(databaseId)
        }
        A.query(sql, values).one(classOf[Integer])
    }

    def updateDatabase(dto: ProjectDatabaseDto): Unit = {
        SqlBox.update("project_database")
            .set("databaseName", dto.getDatabaseName)
            .set("databaseDesc", dto.getDatabaseDesc)
            .set("dbHost", dto.getHost)
            .set("dbPort", dto.getPort)
            .set("userName", dto.getUserName)
            .set("version", dto.getVersion)
            .set("type", dto.getType)
            .set("updateTime", new Date)
            .where.eq("id", dto.getId)
            .and.eq("state", ProjectDatabaseState.COMMON.getState)
            .execute
    }

    def deleteDatabase(projectId: String, dbId: String): Unit = {
        SqlBox.update("project_database")
            .set("state", ProjectDatabaseState.DELETED.getState)
            .set("updateTime", new Date)
            .where.eq("projectId", projectId)
            .and.eq("id", dbId)
            .execute
    }

    def deleteTablesByDatabaseId(projectId: String, dbId: String): Unit = {
        SqlBox.update("project_datatable")
            .set("state", ProjectDataTableState.DELETED.getState)
            .set("updateTime", new Date)
            .where.eq("projectId", projectId)
            .and.eq("databaseId", dbId)
            .execute
    }

    def deleteTableFieldsByDatabaseId(projectId: String, dbId: String): Unit = {
        SqlBox.update("datatable_field")
            .set("state", DataTableFieldState.DELETED.getState)
            .set("updateTime", new Date)
            .where.eq("projectId", projectId)
            .and.eq("databaseId", dbId)
            .execute
    }

    def deleteTableIndexesByDatabaseId(projectId: String, dbId: String): Unit = {
        SqlBox.update("datatable_index")
            .set("state", DataTableFieldState.DELETED.getState)
            .set("updateTime", new Date)
            .where.eq("projectId", projectId)
            .and.eq("databaseId", dbId)
            .execute
    }

    def pageQueryTables(tableName: String, dbId: String, projectId: String, page: Integer): Pager[ProjectDataTableVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT d.id,d.tableName,d.tableDesc,b.type type,d.updateTime,u.nickName createUser FROM project_datatable d ")
        sql.append(" JOIN project_database b ON d.databaseId = b.id ")
        sql.append(" JOIN project p ON d.projectId = p.id JOIN users u ON d.userId = u.id ")
        sql.append(" WHERE d.state = ? AND p.state = ? AND d.projectId = ? AND databaseId = ? ")
        values.add(ProjectDataTableState.COMMON.getState.asInstanceOf[Integer])
        values.add(ProjectState.COMMON.getState.asInstanceOf[Integer])
        values.add(projectId)
        values.add(dbId)
        if (Utils.isNotEmpty(tableName)) {
            sql.append(" AND d.tableName LIKE ? ")
            values.add(s"%$tableName%")
        }
        sql.append(" ORDER BY d.tableName ")
        A.page(sql.toString, values, page, classOf[ProjectDataTableVO])
    }

    def countTableByTableName(tableName: String, tableId: String, projectId: String, dbId: Long): Integer = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT COUNT(id) FROM project_datatable WHERE projectId = ? AND state = ? AND tableName = ? ")
        sql.append(" AND databaseId = ? ")
        values.add(projectId)
        values.add(ProjectDataTableState.COMMON.getState.asInstanceOf[Integer])
        values.add(tableName)
        values.add(dbId.asInstanceOf[java.lang.Long])
        if (Utils.isNotEmpty(tableId)) {
            sql.append(" AND id <> ? ")
            values.add(tableId)
        }
        A.query(sql.toString, values).one(classOf[Integer])
    }

    def insertTable(table: ProjectDataTable): Unit = {
        SqlBox.insert(table)
    }

    def updateTable(dto: ProjectDataTableDto): Unit = {
        SqlBox.update("project_datatable")
            .set("tableName", dto.getTableName)
            .set("tableDesc", dto.getTableDesc)
            .set("updateTime", new Date)
            .where.eq("projectId", dto.getProjectId)
            .and.eq("databaseId", dto.getDatabaseId)
            .and.eq("id", dto.getTableId)
            .execute
    }

    def deleteTable(tableId: String, projectId: String): Unit = {
        SqlBox.update("project_datatable")
            .set("state", ProjectDataTableState.DELETED.getState)
            .set("updateTime", new Date)
            .where.eq("projectId", projectId)
            .and.eq("id", tableId)
            .execute
    }

    def deleteTableAllFields(tableId: String, projectId: String): Unit = {
        SqlBox.update("datatable_field")
            .set("state", DataTableFieldState.DELETED.getState)
            .set("updateTime", new Date)
            .where.eq("projectId", projectId)
            .and.eq("datatableId", tableId)
            .execute
    }

    def deleteTableAllIndexes(tableId: String, projectId: String): Unit = {
        SqlBox.update("datatable_index")
            .set("state", DataTableIndexState.DELETED.getState)
            .set("updateTime", new Date)
            .where.eq("projectId", projectId)
            .and.eq("datatableId", tableId)
            .execute
    }

    def queryTableFieldMaxVersion(tableId: String, projectId: String): String = {
        val sql = "SELECT MAX(version) FROM datatable_field WHERE projectId = ? AND datatableId = ?"
        A.query(sql, projectId, tableId).one(classOf[String])
    }

    def queryTableFieldVersions(tableId: String, projectId: String): util.List[String] = {
        val sql = "SELECT version FROM datatable_field WHERE projectId = ? AND datatableId = ? GROUP BY version" + " ORDER BY version DESC"
        A.query(sql, projectId, tableId).list(classOf[String])
    }

    def queryTableIndexesVersions(tableId: String, projectId: String): util.List[String] = {
        val sql = "SELECT version FROM datatable_index WHERE projectId = ? AND datatableId = ? GROUP BY version" + " ORDER BY version DESC "
        A.query(sql, projectId, tableId).list(classOf[String])
    }

    def queryTableFields(tableId: String, projectId: String, version: String): util.List[DataTableFieldVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT f.id, f.fieldName, f.type, f.length, f.notNull, f.pk, f.autoIncrement, ")
        sql.append(" f._unsigned, f._zerofill, f._charset, f._collation, f._binary, f.onUpdateCT, f._decimal, f.valueList, ")
        sql.append(" f.defaultValue, f.notes, f.indexes, f.indexesName, f.state, f.marker, f.version, u.nickName createUserName ")
        sql.append(" FROM datatable_field f LEFT JOIN users u ON f.userId = u.id ")
        sql.append(" WHERE f.projectId = ? AND f.datatableId = ? AND f.state = ? ")
        values.add(projectId)
        values.add(tableId)
        values.add(DataTableFieldState.COMMON.getState.asInstanceOf[Integer])
        if (Utils.isEmpty(version)) {
            sql.append(" AND f.version = (SELECT MAX(version) FROM datatable_field WHERE projectId = ? AND datatableId = ?) ")
            values.add(projectId)
            values.add(tableId)
        }
        else {
            sql.append(" AND f.version = ? ")
            values.add(version)
        }
        sql.append(" ORDER BY f.sequence ")
        A.query(sql.toString, values).result((rs: ResultSet) => {
            def foo(rs: ResultSet) = {
                val result = new util.LinkedList[DataTableFieldVO]
                while ( {
                    rs.next
                }) {
                    val vo = new DataTableFieldVO
                    vo.setId(rs.getString("id"))
                    vo.setFieldName(rs.getString("fieldName"))
                    vo.setType(rs.getString("type"))
                    vo.setLength(rs.getString("length"))
                    vo.setNotNull(rs.getString("notNull"))
                    vo.setPk(rs.getString("pk"))
                    vo.setAutoIncrement(rs.getString("autoIncrement"))
                    vo.setDefaultValue(rs.getString("defaultValue"))
                    vo.unsigned = rs.getString("_unsigned")
                    vo.zerofill = rs.getString("_zerofill")
                    vo.charset = rs.getString("_charset")
                    vo.collation = rs.getString("_collation")
                    vo.binary = rs.getString("_binary")
                    vo.setOnUpdateCT(rs.getString("onUpdateCT"))
                    vo.decimal = rs.getString("_decimal")
                    val valueList = rs.getString("valueList")
                    var valueListArr = Array[String]()
                    if (Utils.isNotEmpty(valueList)) valueListArr = valueList.split(",")
                    vo.setValueList(valueListArr)
                    vo.setNotes(rs.getString("notes"))
                    vo.setState(rs.getInt("state"))
                    vo.setMarker(rs.getString("marker"))
                    vo.setVersion(rs.getString("version"))
                    vo.setCreateUserName(rs.getString("createUserName"))
                    result.add(vo)
                }
                result
            }

            foo(rs)
        })
    }

    def queryTableIndexes(tableId: String, projectId: String, version: String): util.List[DataTableIndexesVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT i.id, i.indexName, i.fieldNames, i.indexType, i.indexMethod, i.state, i.version, u.nickName createUserName ")
        sql.append(" FROM datatable_index i LEFT JOIN users u ON i.userId = u.id ")
        sql.append(" WHERE i.projectId = ? AND i.datatableId = ? AND i.state = ? ")
        values.add(projectId)
        values.add(tableId)
        values.add(DataTableIndexState.COMMON.getState.asInstanceOf[Integer])
        if (Utils.isEmpty(version)) {
            sql.append(" AND i.version = (SELECT MAX(version) FROM datatable_index WHERE projectId = ? AND datatableId = ?) ")
            values.add(projectId)
            values.add(tableId)
        }
        else {
            sql.append(" AND i.version = ? ")
            values.add(version)
        }
        sql.append(" ORDER BY i.sequence ")
        A.query(sql.toString, values).result((rs: ResultSet) => {
            def foo(rs: ResultSet) = {
                val result = new util.LinkedList[DataTableIndexesVO]
                while ( {
                    rs.next
                }) {
                    val vo = new DataTableIndexesVO
                    vo.id = rs.getString("id")
                    vo.indexName = rs.getString("indexName")
                    val fieldNames = rs.getString("fieldNames")
                    var fieldNamesArr = Array[String]()
                    if (Utils.isNotEmpty(fieldNames)) fieldNamesArr = fieldNames.split(",")
                    vo.fieldNames = fieldNamesArr
                    vo.indexType = rs.getString("indexType")
                    vo.indexMethod = rs.getString("indexMethod")
                    vo.createUserName = rs.getString("createUserName")
                    vo.version = rs.getString("version")
                    vo.state = rs.getInt("state")
                }
                result
            }

            foo(rs)
        })
    }

    def batchInsertTableFields(fields: util.List[DataTableField], projectId: String, datatableId: String, dbId: Long, version: String, userId: String): Unit = {
        val sql = "INSERT INTO datatable_field (id, fieldName, type, length, " +
            "notNull, pk, autoIncrement, defaultValue, notes, indexes, indexesName, state, marker, " +
            "version, datatableId, projectId, userId, createTime, updateTime, sequence, databaseId) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        A.batch(sql, DataTableAdapter.getInsertListValues(fields, projectId, datatableId, dbId, version, userId))
    }


}
