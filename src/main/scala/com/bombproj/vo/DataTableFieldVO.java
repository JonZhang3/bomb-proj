package com.bombproj.vo;

import lombok.Data;

@Data
public class DataTableFieldVO {

    private String name;
    private String id;
    private String fieldName;
    private String type;
    private String length;
    private String notNull;
    private String pk;
    private String autoIncrement;
    private String defaultValue;
    private String _unsigned;
    private String _zerofill;
    private String _charset;
    private String _collation;
    private String _binary;
    private String onUpdateCT;
    private String _decimal;
    private String[] valueList;
    private String notes;
    private String[] indexes;
    private String indexesName;
    private Integer state;
    private String marker;
    private String version;
    private String createUserName;

}
