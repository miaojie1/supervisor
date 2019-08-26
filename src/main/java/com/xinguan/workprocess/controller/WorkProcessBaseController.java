package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workprocess.service.*;
import com.xinguan.workresult.service.DocumentCategoryService;
import com.xinguan.workresult.service.DocumentFolderService;
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
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected FileCategoryService fileCategoryService;
    @Autowired
    protected SiteAcceptanceService siteAcceptanceService;
    @Autowired
    protected ConferenceService conferenceService;
    @Autowired
    protected SideStationService sideStationService;
    @Autowired
    protected WitnessSamplingService witnessSamplingService;
    @Autowired
    protected PatrolService patrolService;
    @Autowired
    protected ConferenceSummaryService conferenceSummaryService;
    @Autowired
    protected CheckAcceptanceService checkAcceptanceService;
    @Autowired
    protected DocumentAuditService documentAuditService;
    @Autowired
    protected DocumentFolderService documentFolderService;
    @Autowired
    protected DocumentCategoryService documentCategoryService;
    @Autowired
    protected ParallelTestService parallelTestService;
}
