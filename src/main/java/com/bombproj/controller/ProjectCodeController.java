package com.bombproj.controller;

import com.bombproj.dto.ProjectCodeDto;
import com.bombproj.framework.JsonResult;
import com.bombproj.framework.interceptor.AuthPassport;
import com.bombproj.service.ProjectCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectCodeController {

    @Autowired
    private ProjectCodeService projectCodeService;

    @AuthPassport
    @GetMapping("/{projectId}/code")
    public JsonResult pageListProjectCodes(@PathVariable("projectId") String projectId,
                                           @ModelAttribute ProjectCodeDto dto) {
        dto.setProjectId(projectId);
        return JsonResult.success(this.projectCodeService.pageListProjectCodes(dto));
    }

    @AuthPassport
    @PostMapping("/{projectId}/code")
    public JsonResult addProjectCode(@PathVariable("projectId") String projectId,
                                     @ModelAttribute @Validated(ProjectCodeDto.AddOrUpdateProjectCodeGroup.class) ProjectCodeDto dto) {
        dto.setProjectId(projectId);
        this.projectCodeService.addProjectCode(dto);
        return JsonResult.success();
    }

    @AuthPassport
    @PutMapping("/{projectId}/code")
    public JsonResult updateProjectCode(@PathVariable("projectId") String projectId,
                                        @ModelAttribute ProjectCodeDto dto) {
        dto.setProjectId(projectId);
        return JsonResult.success();
    }

}
