package com.bombproj.vo;

import lombok.Data;

import java.util.List;

@Data
public class DataTableFieldIndexesVO {

    private String value;
    private String label;
    private List<DataTableFieldIndexesVO> children;

}
