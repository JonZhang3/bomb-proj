package com.bombproj.model;

import com.queryflow.annotation.Table;
import lombok.Data;

@Data
@Table("datatable_indexes")
public class DataTableIndexes {

    private Long id;
    private String type;
    private String method;
    private String sort;
    private String dbType;
    private Integer sequence;

}
