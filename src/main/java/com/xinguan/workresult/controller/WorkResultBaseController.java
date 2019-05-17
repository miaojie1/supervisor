package com.xinguan.workresult.controller;

import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workresult.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public abstract class WorkResultBaseController {
    @Autowired
    protected PictureService pictureService;
    @Autowired
    protected PictureFolderService pictureFolderService;
    @Autowired
    protected SupervisionLogService supervisionLogService;
    @Autowired
    protected SuperviLogRecordService superviLogRecordService;
    @Autowired
    protected DocumentFolderService documentFolderService;
    @Autowired
    protected DocumentService documentService;
    @Autowired
    protected DocumentCategoryService documentCategoryService;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected AccountRecordService accountRecordService;
    @Autowired
    protected StudyMaterialCategoryService studyMaterialCategoryService;
    @Autowired
    protected StudyMaterialService studyMaterialService;
}
