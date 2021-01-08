package com.bombproj.dao

import java.util
import java.util.{LinkedList, List}

import com.bombproj.constants.{ProjectFileState, SortValue}
import com.bombproj.dto.ProjectFileDto
import com.bombproj.model.ProjectFile
import com.bombproj.vo.ProjectAllFileVO
import com.queryflow.accessor.A
import com.queryflow.page.Pager
import org.springframework.stereotype.Repository

@Repository
class ProjectFilesDao {

    def pageQueryAllProjectFiles(dto: ProjectFileDto): Pager[ProjectAllFileVO] = {
        val sql = new StringBuilder
        val values = new util.LinkedList[AnyRef]
        sql.append(" SELECT f.id, f.fileName, f.suffix, f.size, f.creator, u.nickName creatorName, f.updateTime,f.type, ")
        sql.append(" f.starred,f.shared,f.parentId, f.version, f.fullPath ")
        sql.append(" FROM project_file f LEFT JOIN ")
        sql.append(" (SELECT f2.parentId id,COUNT( f2.id ) childCount FROM project_file f2 WHERE f2.state = ? AND f2.projectId=? ")
        sql.append(" GROUP BY f2.parentId) c ON f.id = c.id ")
        sql.append(" LEFT JOIN users u ON f.creator = u.id ")
        sql.append(" WHERE f.projectId = ? AND f.state = ? AND parentId = ? ")
        values.add(ProjectFileState.NORMAL.getState)
        values.add(dto.projectId)
        values.add(dto.projectId)
        values.add(ProjectFileState.NORMAL.getState)
        values.add(dto.folderId)
        // name、creatorName、updateTime、size
        // desc、asc
        dto.sortName match {
            case "creator" => sql.append(" ORDER BY creatorName ").append(SortValue.ofValue(dto.getSortValue))
            case "updateTime" => sql.append(" ORDER BY f.updateTime ").append(SortValue.ofValue(dto.getSortValue))
            case "size" => sql.append(" ORDER BY f.size ").append(SortValue.ofValue(dto.getSortValue))
            case _ => sql.append(" ORDER BY f.fileName ").append(SortValue.ofValue(dto.getSortValue))
        }
        A.page(sql.toString, values, dto.getPage, dto.getPageSize, classOf[ProjectAllFileVO])
    }

    def insertProjectFile(file: ProjectFile): Unit = {
    }

}
