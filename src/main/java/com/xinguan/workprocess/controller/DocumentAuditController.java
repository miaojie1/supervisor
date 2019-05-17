package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.AttachmentService;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.DocumentAudit;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workresult.model.AccountRecord;
import com.xinguan.workresult.model.Document;
import com.xinguan.workresult.model.DocumentCategory;
import com.xinguan.workresult.model.DocumentFolder;
import com.xinguan.workresult.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/documentAudit")
@Api(value = "文件审核相关接口")
public class DocumentAuditController extends WorkProcessBaseController {

    @Autowired
    DocumentService documentService;
    @Autowired
    DocumentCategoryService documentCategoryService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    DocumentFolderService documentFolderService;
    @Autowired
    AccountRecordService accountRecordService;
    @Autowired
    AccountCategoryService accountCategoryService;
    @PostMapping(value = "/listAllProjects")
    @ApiOperation(value = "获取所有项目信息")
    public List<Project> listAllProjects() {
        return projectService.listAllProjects();
    }

    @PostMapping(value = "/listAllFolders")
    @ApiOperation(value = "获取所有文件夹")
    public List<DocumentFolder> listAllFolders() {
        return documentFolderService.listAllDocuFolder();
    }


    @PostMapping(value = "/listAllCategorys")
    @ApiOperation(value = "获取所有文件夹类别")
    public List<DocumentCategory> listAllCategorys() {
        return documentCategoryService.listAllDocumentCategories();
    }


    @PostMapping(value = "/allotAuditTask")
    @ApiOperation(value = "总监分配审核任务")
    public ResultInfo allotAuditTask(@ApiParam(name = "documentAuditId",required = true,value = "审核文件任务ID") @RequestParam Long documentAuditId,
                                     @ApiParam(name = "employees",required = true,value = "需要审核文档的人员列表") @RequestBody List<Employee> employees) {

        ResultInfo resultInfo = new ResultInfo();
        try {
            documentAuditService.allotAuditTask(documentAuditId,employees);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }


    @PostMapping(value = "/saveDocumentAudit")
    @ApiOperation(value = "增加或修改 文件审核")
    public ResultInfo saveSiteAcceptance(
            @ApiParam(name = "documentAudit", required = true, value = "待保存的对象") @RequestBody DocumentAudit documentAudit,
            @ApiParam(name = "documentId", required = true, value = "文档的id") @RequestParam Long documentId,
            @ApiParam(name = "categoryId", required = true, value = "设置文档的类别") @RequestParam Long categoryId,
            @ApiParam(name = "folderId", required = true, value = "设置文档的文件夹") @RequestParam Long folderId,
            HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            if (documentAudit.getId() == null) {
                Document toSaveDoc = documentService.findDocuById(documentId);
                if (toSaveDoc != null) {
                    // 根据文档id 找到文档 ，并设置他的文档类别，文件夹
                    toSaveDoc.setDocumentCategory(documentCategoryService.findDocuCateById(categoryId));
                    toSaveDoc.setDocumentFolder(documentFolderService.findDocuFolderById(folderId));
                    documentAudit.setDocument(toSaveDoc);
                }

                documentAudit.setCreateDate(new Date());
                documentAudit.setSponsor(employeeService.getCurrentUser());
                documentAudit.setDepartment(employeeService.getCurrentUser().getDepartment());
                documentAudit.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
                resultInfo.setMessage("添加审核文件成功！");
            }else {
                if (!StringUtils.isEmpty(documentId)){
                    documentService.removeDocument(documentAuditService.findById(documentAudit.getId()).getDocument().getId());
                    Document toSaveDoc = documentService.findDocuById(documentId);
                    if (!StringUtils.isEmpty(categoryId)){
                        toSaveDoc.setDocumentCategory(documentCategoryService.findDocuCateById(categoryId));
                    }
                    if(!StringUtils.isEmpty(folderId)){
                        toSaveDoc.setDocumentFolder(documentFolderService.findDocuFolderById(folderId));
                    }
                    documentAudit.setModificationDate(new Date());
                    resultInfo.setMessage("修改审核文件成功！");
                }else {
                    Document document = documentService.findDocuById(documentAudit.getDocument().getId());
                    if (!StringUtils.isEmpty(categoryId)){
                        document.setDocumentCategory(documentCategoryService.findDocuCateById(categoryId));
                    }
                    if(!StringUtils.isEmpty(folderId)){
                        document.setDocumentFolder(documentFolderService.findDocuFolderById(folderId));
                    }
                    documentAudit.setModificationDate(new Date());
                    resultInfo.setMessage("修改审核文件成功！");
                }
            }
            DocumentAudit haveSaveDocAudit=documentAuditService.saveDocumentAudit(documentAudit);
            AccountRecord toSaveAcc = new AccountRecord();
            if (accountRecordService.getAccByRecordId(haveSaveDocAudit.getId())==null){
                toSaveAcc.setRecordId(haveSaveDocAudit.getId());
            } else {
                toSaveAcc = accountRecordService.getAccByRecordId(haveSaveDocAudit.getId());
            }
            toSaveAcc.setRecordName(haveSaveDocAudit.getTitle());
            toSaveAcc.setDepartment(haveSaveDocAudit.getDepartment());
            toSaveAcc.setAccountCategory(accountCategoryService.getAccCategoryByName("文件审核"));
            accountRecordService.saveAccountRecord(toSaveAcc);
            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加审核文件失败！");
        }
        return  resultInfo;
    }

    @PostMapping(value = "/listDocumentAuditsByDepart")
    @ApiOperation(value = "列出 文件审核列表")
    public Page<DocumentAudit> listDocumentAuditsByDepart(
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @RequestParam("pageSize") int pageSize,
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @RequestParam("pageNo") int pageNo,
            @ApiParam(name = "name", required = true, value = "文件名称") @RequestParam(name = "name") String name){
        return documentAuditService.listDocumentAuditByDocName(pageNo,pageSize,name);
    }

    @PostMapping(value = "/deleteDocAuditById")
    @ApiOperation(value = "根据id删除文件审核 以及相关的文档")
    public ResultInfo deleteDocAuditById(
            @ApiParam(name = "docAuditId", required = true, value = "文件审核的id") @RequestParam("docAuditId") Long docAuditId,
            @ApiParam(name = "docId", required = true, value = "文档的id") @RequestParam("docId") Long docId){
        ResultInfo resultInfo=new ResultInfo();
        try {
            documentAuditService.deleteDocAuditById(docAuditId);
            documentService.removeDocument(docId);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除审核文件记录以及相关文件成功！");
        }catch (Exception e){
            resultInfo.setMessage("删除审核文件记录以及相关文件失败！");
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
        }
        return resultInfo;
    }

    @PostMapping(value = "/deleteDocById")
    @ApiOperation(value = "根据id删除文档")
    public ResultInfo deleteDocById(
            @ApiParam(name = "docId", required = true, value = "文档的id") @RequestParam("docId") Long docId){
        ResultInfo resultInfo=new ResultInfo();
        try {
            documentService.removeDocument(docId);
            resultInfo.setStatus(true);
            resultInfo.setMessage("已删除上传文件！");
        }catch (Exception e){
            resultInfo.setMessage("删除上传文档失败！");
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
        }
        return resultInfo;
    }

    @ApiOperation(value = "下载文档")
    @GetMapping("/downloadDoc")
    public void downloadFile(@RequestParam("filePath")String filePath, HttpServletResponse response) throws ServletException, IOException {
        documentService.downloadDocument(filePath, response);
    }
}
