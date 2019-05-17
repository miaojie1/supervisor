package com.xinguan.workresult.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.StudyMaterial;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api("学习资料相关接口")
@RestController
@RequestMapping("/studyMaterial")
public class StudyMaterialController extends WorkResultBaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(StudyMaterialController.class);
    @PostMapping(value = "/listStudyMaterial/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取学习资料")
    public PageInfo<StudyMaterial> listStudyMaterial(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                        @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                        @ApiParam(name = "departmentId", value = "根据部门查询")String departmentId,
                                                        @ApiParam(name = "studyMaterialName", value = "文件名称模糊查询") String studyMaterialName,
                                                        @ApiParam(name = "studyMaterialCategoryId", value = "根据学习资料类别查询") String studyMaterialCategoryId) {
        Page<StudyMaterial> studyMaterials = studyMaterialService.listStudyMaterialByPage(pageSize,pageNo,departmentId,studyMaterialName,studyMaterialCategoryId);
        Map<String, Object> param = Maps.newHashMap("param",departmentId+studyMaterialName+studyMaterialCategoryId );
        return new PageInfo<>(studyMaterials,param);
    }
    @ApiOperation(value = "下载学习资料附件")
    @GetMapping("/downloadStudyMaterialAttachment")
    public void downloadStudyMaterialAttachment(@RequestParam("filePath")String filePath, HttpServletResponse response) throws ServletException, IOException {
        studyMaterialService.downloadStudyMaterialAttachment(filePath,response);
    }

    @PostMapping(value = "/uploadStudyMaterialAttachment")
    @ApiOperation(value = "学习资料附件上传方法")
    public void uploadStudyMaterialAttachment(@RequestParam("file") MultipartFile multipartFile,
                                              @RequestParam("studyMaterialId") String studyMaterialId) {
        studyMaterialService.uploadStudyMaterialAttachment(multipartFile,studyMaterialId);
    }

    @PostMapping(value = "/deleteStudyMaterialAttachment")
    @ApiOperation(value = "学习资料附件删除方法")
    public void deleteStudyMaterialAttachment(@RequestParam("studyMaterialId") String studyMaterialId) {
        studyMaterialService.deleteStudyMaterialAttachment(studyMaterialId);
    }

    @PostMapping(value = "/saveStudyMaterial")
    @ApiOperation(value = "学习资料新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "studyMaterial", required = true, value = "待保存的对象") @RequestBody StudyMaterial studyMaterial) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            StudyMaterial result = studyMaterialService.saveOrUpdate(studyMaterial);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/studyMaterialId/{studyMaterialId}")
    @ApiOperation(value = "根据studyMaterial ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "studyMaterialId", required = true, value = "需要删除的studyMaterial ID") @PathVariable String studyMaterialId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            studyMaterialService.deleteById(Long.parseLong(studyMaterialId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除studyMaterial失败：id:" + studyMaterialId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
