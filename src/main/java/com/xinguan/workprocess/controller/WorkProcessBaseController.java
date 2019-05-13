package com.xinguan.workprocess.controller;

import com.xinguan.workprocess.model.Project;
import com.xinguan.workprocess.model.ProjectStatus;
import com.xinguan.workprocess.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public abstract class WorkProcessBaseController {
    @Autowired
    protected ProjectService projectService;
    @Autowired
    protected ProjectStatusService projectStatusService;
    @Autowired
    protected ProjectSupervisionDepartmentService projectSupervisionDepartmentService;
    @Autowired
    protected KnowledgeService knowledgeService;
    @Autowired
    protected FileFolderService fileFolderService;
}
