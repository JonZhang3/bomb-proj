package com.bombproj.model;

import com.queryflow.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@ToString
@Table("datatable_field")
public class DataTableField {

    private String id;
    private String fieldName;
    private String type;
    private String length;
    private Integer notNull;
    private String defaultValue;
    private String notes;
    private String indexes;
    private String indexesName;
    private Integer state;
    private String marker;
    private String version;
    private String datatableId;
    private String projectId;
    private String userId;
    private Date createTime;
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTableField that = (DataTableField) o;
        return Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(type, that.type) &&
            Objects.equals(length, that.length) &&
            Objects.equals(notNull, that.notNull) &&
            Objects.equals(defaultValue, that.defaultValue) &&
            Objects.equals(indexes, that.indexes) &&
            Objects.equals(indexesName, that.indexesName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fieldName, type, length, notNull, defaultValue, notes, indexes, indexesName, state,
            marker, version, datatableId, projectId, userId, createTime, updateTime);
    }
}
