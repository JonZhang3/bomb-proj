package com.bombproj.controller;

import com.bombproj.dto.ProjectFileDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.SessionConfig;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.service.ProjectFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/project")
public class ProjectFilesController {

    @Autowired
    private ProjectFilesService projectFilesService;

    @AuthPassport
    @GetMapping("{projectId}/folders/{folderId}")
    public JsonResult listFiles(@PathVariable("projectId") String projectId,
                                @PathVariable("folderId") String folderId,
                                @ModelAttribute ProjectFileDto dto) {
        dto.setProjectId(projectId);
        dto.setFolderId(folderId);
        return JsonResult.success(this.projectFilesService.pageProjectFiles(dto));
    }

    @AuthPassport
    @PostMapping("{projectId}/folders/{folderId}/mkdir")
    public JsonResult mkdir(@PathVariable("projectId") String projectId,
                            @PathVariable("folderId") String folderId,
                            @ModelAttribute ProjectFileDto dto) {
        SessionConfig sessionConfig = SessionConfig.current();
        dto.setProjectId(projectId);
        dto.setFolderId(folderId);
        dto.setCreatUserId(sessionConfig.getUserId());
        return JsonResult.success();
    }

}
