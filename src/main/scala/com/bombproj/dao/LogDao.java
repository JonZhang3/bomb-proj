package com.bombproj.dao;

import com.bombproj.model.Log;
import com.queryflow.sql.SqlBox;
import org.springframework.stereotype.Repository;

@Repository
public class LogDao {

    public void insertLog(Log log) {
        SqlBox.insert(log);
    }

}
