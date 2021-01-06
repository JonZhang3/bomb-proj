package com.bombproj.vo;

import lombok.Data;

@Data
public class ProjectAllFileVO {

    private String id;
    private String fileName;
    private String suffix;
    private Long size;
    private String creator;
    private String creatorName;
    private String updateTime;
    private Integer type;
    private Integer starred;
    private Integer shared;
    private String version;
    private String parentId;
    private String fullPath;

}
