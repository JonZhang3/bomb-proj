package com.bombproj.controller;

import com.bombproj.dto.ProjectDataTableDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.service.ProjectDataTableService;
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
public class ProjectDataTableController {

    @Autowired
    private ProjectDataTableService projectDataTableService;

    @AuthPassport
    @GetMapping("/field-types")
    public JsonResult getFieldTypesByDbType(@RequestParam("dbType") String dbType) {
        return JsonResult.success(this.projectDataTableService.getFieldTypes(dbType));
    }

    @AuthPassport
    @GetMapping("/field-indexes")
    public JsonResult getIndexes(@RequestParam("dbType") String dbType) {
        return JsonResult.success(this.projectDataTableService.getFieldIndexes(dbType));
    }

    @AuthPassport
    @GetMapping("/{projectId}/datatable")
    public JsonResult pageListTables(@PathVariable("projectId") String projectId,
                                     @RequestParam(value = "tableName", required = false) String tableName,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page) {
        SessionConfig sessionConfig = SessionConfig.current();
        return JsonResult.success(this.projectDataTableService.pageListTables(tableName, projectId, page));
    }

    @AuthPassport
    @PostMapping("/{projectId}/datatable")
    public JsonResult addTable(@PathVariable("projectId") String projectId,
                               @ModelAttribute ProjectDataTableDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setProjectId(projectId);
        dto.setUserId(sessionConfig.getUserId());
        this.projectDataTableService.addTable(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @PutMapping("/{projectId}/datatable/{tableId}")
    public JsonResult updateTable(@PathVariable("projectId") String projectId,
                                  @PathVariable("tableId") String tableId,
                                  @ModelAttribute ProjectDataTableDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setUserId(sessionConfig.getUserId());
        dto.setProjectId(projectId);
        dto.setTableId(tableId);
        this.projectDataTableService.updateTable(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @DeleteMapping("/{projectId}/datatable/{tableId}")
    public JsonResult deleteTable(@PathVariable("projectId") String projectId,
                                  @PathVariable("tableId") String tableId) {
        SessionConfig sessionConfig = SessionConfig.current();
        ProjectDataTableDto dto = new ProjectDataTableDto();
        dto.setProjectId(projectId);
        dto.setTableId(tableId);
        dto.setUserId(sessionConfig.getUserId());
        this.projectDataTableService.deleteTable(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @GetMapping("/{projectId}/datatable/{tableId}/fields")
    public JsonResult getTableFields(@PathVariable("projectId") String projectId,
                                     @PathVariable("tableId") String tableId,
                                     @RequestParam("version") String version) {
        SessionConfig sessionConfig = SessionConfig.current();
        // TODO check permission
        return JsonResult.success(this.projectDataTableService.getTableFields(tableId, projectId, version));
    }

    @AuthPassport
    @PostMapping("/{projectId}/datatable/{tableId}/fields")
    public JsonResult addTableFields(@PathVariable("projectId") String projectId,
                                     @PathVariable("tableId") String tableId,
                                     @RequestParam("fields") String fields) {

        return null;
    }

}
