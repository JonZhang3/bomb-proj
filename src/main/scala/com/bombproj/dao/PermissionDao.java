package com.bombproj.dao;

import com.bombproj.constants.PermissionState;
import com.bombproj.constants.PermissionType;
import com.bombproj.model.Permission;
import com.queryflow.accessor.A;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class PermissionDao {

    public List<Permission> queryAllPermission(PermissionType type) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new LinkedList<>();
        sql.append(" SELECT * FROM permission WHERE state = ? ");
        values.add(PermissionState.COMMON.getState());
        if(type != null) {
            sql.append(" AND type = ? ");
            values.add(type.getType());
        }
        sql.append(" ORDER BY sequence ");
        return A.query(sql.toString(), values).list(Permission.class);
    }

}
