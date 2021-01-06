package com.bombproj.model;

import com.queryflow.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Table("log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Integer state;
    private Date createTime;
    private String modelClass;
    private String modelName;
    private String type;
    private String updateUserId;
    private String updateUserName;
    private String remark;
    private String content;

}
