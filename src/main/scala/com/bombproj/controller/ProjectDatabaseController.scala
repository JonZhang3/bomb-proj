package com.bombproj.controller

import com.bombproj.dto.{ProjectDataTableDto, ProjectDatabaseDto}
import com.bombproj.framework.{JsonResult, SessionConfig}
import com.bombproj.framework.interceptor.AuthPassport
import com.bombproj.service.ProjectDatabaseService
import javax.annotation.Resource
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, ModelAttribute, PathVariable, PostMapping, PutMapping, RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/api/project"))
class ProjectDatabaseController @Resource()(projectDatabaseService: ProjectDatabaseService) {

    @AuthPassport
    @GetMapping(Array("/field-types"))
    def getFieldTypesByDbType(@RequestParam("dbType") dbType: String): JsonResult =
        JsonResult.success(data = this.projectDatabaseService.getFieldTypes(dbType))

    @AuthPassport
    @GetMapping(Array("/field-indexes"))
    def getIndexes(@RequestParam("dbType") dbType: String): JsonResult =
        JsonResult.success(data = this.projectDatabaseService.getFieldIndexes(dbType))

    /**
     * 查询指定项目中的数据库列表
     *
     * @param projectId    项目 ID
     * @param databaseName 查询条件：数据库名
     */
    @AuthPassport
    @GetMapping(Array("/{projectId}/db"))
    def pageListDatabase(@PathVariable("projectId") projectId: String,
                         @RequestParam(value = "databaseName", required = false) databaseName: String): JsonResult =
        JsonResult.success(data = this.projectDatabaseService.listDatabases(projectId, databaseName))

    /**
     * 新增数据库
     *
     * @param projectId 项目 ID
     * @param dto       请求参数，包含了数据库信息
     */
    @AuthPassport
    @PostMapping(Array("/{projectId}/db"))
    def addDatabase(@PathVariable("projectId") projectId: String, @ModelAttribute dto: ProjectDatabaseDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.setProjectId(projectId)
        dto.setUserId(sessionConfig.userId)
        this.projectDatabaseService.addDatabase(dto)
        JsonResult.success()
    }

    /**
     * 更新数据库信息
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     * @param dto       要更新的数据库信息
     */
    @AuthPassport
    @PutMapping(Array("/{projectId}/db/{dbId}"))
    def updateDatabase(@PathVariable("projectId") projectId: String,
                       @PathVariable("dbId") dbId: String,
                       @ModelAttribute dto: ProjectDatabaseDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.setUserId(sessionConfig.userId)
        dto.setProjectId(projectId)
        dto.setId(dbId)
        this.projectDatabaseService.updateDatabase(dto)
        JsonResult.success()
    }

    /**
     * 删除指定的数据库，同时也会删除数据库中的表信息
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     */
    @AuthPassport
    @DeleteMapping(Array("/{projectId}/db/{dbId}"))
    def deleteDatabase(@PathVariable("projectId") projectId: String,
                       @PathVariable("dbId") dbId: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        val dto = new ProjectDatabaseDto
        dto.setProjectId(projectId)
        dto.setId(dbId)
        dto.setUserId(sessionConfig.userId)
        this.projectDatabaseService.deleteDatabase(dto)
        JsonResult.success()
    }

    /**
     * 获取指定数据库的所有表信息
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     * @param tableName 表名
     * @param page      页码
     */
    @AuthPassport
    @GetMapping(Array("/{projectId}/db/{dbId}/table"))
    def pageListTables(@PathVariable("projectId") projectId: String,
                       @PathVariable("dbId") dbId: String,
                       @RequestParam(value = "tableName", required = false) tableName: String,
                       @RequestParam(value = "page", defaultValue = "1") page: Integer): JsonResult =
        JsonResult.success(data = this.projectDatabaseService.pageListTables(tableName, dbId, projectId, page))

    /**
     * 在指定的数据库中新增表
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     * @param dto       新增的表的信息
     */
    @AuthPassport
    @PostMapping(Array("/{projectId}/db/{dbId}/table"))
    def addTable(@PathVariable("projectId") projectId: String,
                 @PathVariable("dbId") dbId: Long,
                 @ModelAttribute dto: ProjectDataTableDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.setProjectId(projectId)
        dto.setUserId(sessionConfig.userId)
        dto.setDatabaseId(dbId)
        JsonResult.success(data = this.projectDatabaseService.addTable(dto))
    }

    /**
     * 更新表信息，包括表名、列信息、索引信息
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     * @param tableId   表 ID
     * @param dto
     */
    @AuthPassport
    @PutMapping(Array("/{projectId}/db/{dbId}/table/{tableId}"))
    def updateTable(@PathVariable("projectId") projectId: String,
                    @PathVariable("dbId") dbId: Long,
                    @PathVariable("tableId") tableId: String,
                    @ModelAttribute dto: ProjectDataTableDto): JsonResult = {
        val sessionConfig = SessionConfig.current()
        dto.setUserId(sessionConfig.userId)
        dto.setProjectId(projectId)
        dto.setTableId(tableId)
        dto.setDatabaseId(dbId)
        this.projectDatabaseService.updateTable(dto)
        JsonResult.success()
    }

    /**
     * 删除指定的表
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     * @param tableId   要删除的表 ID
     */
    @AuthPassport
    @DeleteMapping(Array("/{projectId}/db/{dbId}/table/{tableId}"))
    def deleteTable(@PathVariable("projectId") projectId: String,
                    @PathVariable("dbId") dbId: Long,
                    @PathVariable("tableId") tableId: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        val dto = new ProjectDataTableDto
        dto.setProjectId(projectId)
        dto.setTableId(tableId)
        dto.setUserId(sessionConfig.userId)
        dto.setDatabaseId(dbId)
        this.projectDatabaseService.deleteTable(dto)
        JsonResult.success()
    }

    /**
     * 获取指定表的索引信息
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     * @param tableId   表 ID
     * @param version   版本号
     */
    @AuthPassport
    @GetMapping(Array("/{projectId}/db/{dbId}/table/{tableId}/indexes"))
    def getTableIndexes(@PathVariable("projectId") projectId: String,
                        @PathVariable("dbId") dbId: Long,
                        @PathVariable("tableId") tableId: String,
                        @RequestParam(value = "version", required = false) version: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        // TODO check permission
        JsonResult.success(data = this.projectDatabaseService.getTableIndexes(tableId, projectId, version))
    }

    /**
     * 获取指定表的字段信息
     *
     * @param projectId 项目 ID
     * @param dbId      数据库 ID
     * @param tableId   表 ID
     * @param version   版本号
     */
    @AuthPassport
    @GetMapping(Array("/{projectId}/db/{dbId}/table/{tableId}/fields"))
    def getTableFields(@PathVariable("projectId") projectId: String,
                       @PathVariable("dbId") dbId: Long,
                       @PathVariable("tableId") tableId: String,
                       @RequestParam(value = "version", required = false) version: String): JsonResult =
        JsonResult.success(data = this.projectDatabaseService.getTableFields(tableId, projectId, version))

    @AuthPassport
    @PostMapping(Array("/{projectId}/db/{dbId}/datatable/{tableId}/fields"))
    def addTableFields(@PathVariable("projectId") projectId: String,
                       @PathVariable("dbId") dbId: Long,
                       @PathVariable("tableId") tableId: String,
                       @RequestParam("fields") fields: String): JsonResult = {
        val sessionConfig = SessionConfig.current()
        this.projectDatabaseService.addOrUpdateTableFields(tableId, projectId, sessionConfig.userId, dbId, fields)
        JsonResult.success()
    }

}
