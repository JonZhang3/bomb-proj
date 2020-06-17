package com.bombproj.service;

import com.bombproj.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    public void pageLitProjects(int page) {

    }

}
