package com.bombproj.dao

import java.util
import java.util.{LinkedList, List}

import com.bombproj.constants.{PermissionState, PermissionType}
import com.bombproj.model.Permission
import com.queryflow.accessor.A
import org.springframework.stereotype.Repository

@Repository
class PermissionDao {

    def queryAllPermission(`type`: PermissionType): util.List[Permission] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT * FROM permission WHERE state = ? ")
        values.add(PermissionState.COMMON.getState)
        if (`type` != null) {
            sql.append(" AND type = ? ")
            values.add(`type`.getType)
        }
        sql.append(" ORDER BY sequence ")
        A.query(sql.toString, values).list(classOf[Permission])
    }

}
