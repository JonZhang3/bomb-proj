package com.bombproj.model;

import com.queryflow.annotation.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Byte status;
    private Long sequence;
    private String remark;
    private String userId;
    private Byte type;
    private String password;
    private String cover;
    private Byte lucenSearch;

}
