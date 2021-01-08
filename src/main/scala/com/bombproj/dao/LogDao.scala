package com.bombproj.dao

import com.bombproj.model.Log
import com.queryflow.sql.SqlBox
import org.springframework.stereotype.Repository

@Repository
class LogDao {

    def insertLog(log: Log): Unit = {
        SqlBox.insert(log)
    }

}
