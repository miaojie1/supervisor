package com.xinguan.workresult.controller;

import com.xinguan.workresult.service.DocumentFolderService;
import com.xinguan.workresult.service.DocumentService;
import com.xinguan.workresult.service.PictureFolderService;
import com.xinguan.workresult.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public abstract class WorkResultBaseController {
    @Autowired
    protected PictureService pictureService;
    @Autowired
    protected PictureFolderService pictureFolderService;
    @Autowired
    protected DocumentFolderService documentFolderService;
    @Autowired
    protected DocumentService documentService;
}
