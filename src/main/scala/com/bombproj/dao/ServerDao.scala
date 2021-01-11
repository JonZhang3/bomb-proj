package com.bombproj.dao

import com.bombproj.model.Server
import com.queryflow.sql.SqlBox
import org.springframework.stereotype.Repository

@Repository
class ServerDao {

    def insertServer(server: Server): Unit = {
        SqlBox.insert(server)
    }

    def pageQuery(): Unit = {

    }

}
