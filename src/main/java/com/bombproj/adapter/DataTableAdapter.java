package com.bombproj.adapter;

import com.bombproj.constants.DataTableFieldState;
import com.bombproj.model.DataTableField;
import com.bombproj.utils.ShortID;
import com.bombproj.utils.Utils;
import com.queryflow.key.KeyGenerateUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DataTableAdapter {

    public static List<List<Object>> getInsertListValues(List<DataTableField> fields, String projectId, String datatableId,
                                                         String version, String userId) {
        List<List<Object>> values = new LinkedList<>();
        for (DataTableField field : fields) {
            if(Utils.isEmpty(field.getFieldName()) || Utils.isEmpty(field.getType())) {
                continue;
            }
            List<Object> value = new LinkedList<>();
            value.add(KeyGenerateUtil.generateId() + "");
            value.add(field.getFieldName());
            value.add(field.getType());
            value.add(field.getLength());
            value.add(Utils.isEmpty(field.getNotNull()) ? "0" : field.getNotNull());
            value.add(Utils.isEmpty(field.getPk()) ? "0" : field.getPk());
            value.add(Utils.isEmpty(field.getAutoIncrement()) ? "0" : field.getAutoIncrement());
            value.add(field.getDefaultValue());
            value.add(field.getNotes());
            value.add(field.getIndexes());
            if(Utils.isNotEmpty(field.getIndexes()) && Utils.isEmpty(field.getIndexesName())) {
                value.add(datatableId + "_" + field.getFieldName() + "_" + ShortID.randomID());
            } else {
                value.add(field.getIndexesName());
            }
            value.add(DataTableFieldState.COMMON.getState());
            value.add(field.getMarker());
            value.add(version);
            value.add(datatableId);
            value.add(projectId);
            value.add(userId);
            Date now = new Date();
            value.add(now);
            value.add(now);
            values.add(value);
        }
        return values;
    }

}
