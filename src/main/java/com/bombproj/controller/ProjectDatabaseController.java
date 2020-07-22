package com.bombproj.controller;

import com.bombproj.dto.ProjectDataTableDto;
import com.bombproj.dto.ProjectDatabaseDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.service.ProjectDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectDatabaseController {

    @Autowired
    private ProjectDatabaseService projectDatabaseService;

    @AuthPassport
    @GetMapping("/field-types")
    public JsonResult getFieldTypesByDbType(@RequestParam("dbType") String dbType) {
        return JsonResult.success(this.projectDatabaseService.getFieldTypes(dbType));
    }

    @AuthPassport
    @GetMapping("/field-indexes")
    public JsonResult getIndexes(@RequestParam("dbType") String dbType) {
        return JsonResult.success(this.projectDatabaseService.getFieldIndexes(dbType));
    }

    /**
     * 查询指定项目中的数据库列表
     *
     * @param projectId    项目 ID
     * @param databaseName 查询条件：数据库名
     */
    @AuthPassport
    @GetMapping("/{projectId}/db")
    public JsonResult pageListDatabase(@PathVariable("projectId") String projectId,
                                       @RequestParam(value = "databaseName", required = false) String databaseName) {
        return JsonResult.success(this.projectDatabaseService.listDatabases(projectId, databaseName));
    }

    /**
     * 新增数据库
     *
     * @param projectId 项目 ID
     * @param dto       请求参数，包含了数据库信息
     */
    @AuthPassport
    @PostMapping("/{projectId}/db")
    public JsonResult addDatabase(@PathVariable("projectId") String projectId,
                                  @ModelAttribute ProjectDatabaseDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setProjectId(projectId);
        dto.setUserId(sessionConfig.getUserId());
        this.projectDatabaseService.addDatabase(dto);
        return JsonResult.success();
    }

    /**
     * 更新数据库信息
     *
     * @param projectId 项目 ID
     * @param dbId 数据库 ID
     * @param dto 要更新的数据库信息
     */
    @AuthPassport
    @PutMapping("/{projectId}/db/{dbId}")
    public JsonResult updateDatabase(@PathVariable("projectId") String projectId,
                                     @PathVariable("dbId") String dbId,
                                     @ModelAttribute ProjectDatabaseDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setUserId(sessionConfig.getUserId());
        dto.setProjectId(projectId);
        dto.setId(dbId);
        this.projectDatabaseService.updateDatabase(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @DeleteMapping("/{projectId}/db/{dbId}")
    public JsonResult deleteDatabase(@PathVariable("projectId") String projectId,
                                     @PathVariable("dbId") String dbId) {
        SessionConfig sessionConfig = SessionConfig.current();
        ProjectDatabaseDto dto = new ProjectDatabaseDto();
        dto.setProjectId(projectId);
        dto.setId(dbId);
        dto.setUserId(sessionConfig.getUserId());
        this.projectDatabaseService.deleteDatabase(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @GetMapping("/{projectId}/db/{dbId}/table")
    public JsonResult pageListTables(@PathVariable("projectId") String projectId,
                                     @PathVariable("dbId") String dbId,
                                     @RequestParam(value = "tableName", required = false) String tableName,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return JsonResult.success(this.projectDatabaseService.pageListTables(tableName, dbId, projectId, page));
    }

    @AuthPassport
    @PostMapping("/{projectId}/db/{dbId}/table")
    public JsonResult addTable(@PathVariable("projectId") String projectId,
                               @PathVariable("dbId") Long dbId,
                               @ModelAttribute ProjectDataTableDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setProjectId(projectId);
        dto.setUserId(sessionConfig.getUserId());
        dto.setDatabaseId(dbId);
        this.projectDatabaseService.addTable(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @PutMapping("/{projectId}/db/{dbId}/table/{tableId}")
    public JsonResult updateTable(@PathVariable("projectId") String projectId,
                                  @PathVariable("dbId") Long dbId,
                                  @PathVariable("tableId") String tableId,
                                  @ModelAttribute ProjectDataTableDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setUserId(sessionConfig.getUserId());
        dto.setProjectId(projectId);
        dto.setTableId(tableId);
        dto.setDatabaseId(dbId);
        this.projectDatabaseService.updateTable(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @DeleteMapping("/{projectId}/db/{dbId}/table/{tableId}")
    public JsonResult deleteTable(@PathVariable("projectId") String projectId,
                                  @PathVariable("dbId") Long dbId,
                                  @PathVariable("tableId") String tableId) {
        SessionConfig sessionConfig = SessionConfig.current();
        ProjectDataTableDto dto = new ProjectDataTableDto();
        dto.setProjectId(projectId);
        dto.setTableId(tableId);
        dto.setUserId(sessionConfig.getUserId());
        dto.setDatabaseId(dbId);
        this.projectDatabaseService.deleteTable(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @GetMapping("/{projectId}/db/{dbId}/table/{tableId}/indexes")
    public JsonResult getTableIndexes(@PathVariable("projectId") String projectId,
                                      @PathVariable("dbId") Long dbId,
                                      @PathVariable("tableId") String tableId,
                                      @RequestParam(value = "version", required = false) String version) {
        SessionConfig sessionConfig = SessionConfig.current();
        // TODO check permission
        return JsonResult.success(this.projectDatabaseService.getTableIndexes(tableId, projectId, version));
    }

    @AuthPassport
    @GetMapping("/{projectId}/db/{dbId}/table/{tableId}/fields")
    public JsonResult getTableFields(@PathVariable("projectId") String projectId,
                                     @PathVariable("dbId") Long dbId,
                                     @PathVariable("tableId") String tableId,
                                     @RequestParam(value = "version", required = false) String version) {
        SessionConfig sessionConfig = SessionConfig.current();
        // TODO check permission
        return JsonResult.success(this.projectDatabaseService.getTableFields(tableId, projectId, version));
    }

    @AuthPassport
    @PostMapping("/{projectId}/db/{dbId}/datatable/{tableId}/fields")
    public JsonResult addTableFields(@PathVariable("projectId") String projectId,
                                     @PathVariable("dbId") Long dbId,
                                     @PathVariable("tableId") String tableId,
                                     @RequestParam("fields") String fields) {
        SessionConfig sessionConfig = SessionConfig.current();
        this.projectDatabaseService.addOrUpdateTableFields(tableId, projectId, sessionConfig.getUserId(), dbId, fields);
        return JsonResult.success();
    }

}
