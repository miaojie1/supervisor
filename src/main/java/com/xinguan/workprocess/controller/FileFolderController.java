package com.xinguan.workprocess.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xinguan.usermanage.controller.BaseController;
import com.xinguan.utils.MD5Util;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.FileFolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api("文件夹相关接口")
@RestController
@RequestMapping("/fileFolder")
public class FileFolderController extends WorkProcessBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileFolderController.class);

    @PostMapping(value = "/listAllFileFolder")
    @ApiOperation("获取所有的文件夹不分页")
    public List<FileFolder> listAllEmployeeStatus() {
        return fileFolderService.listAllFileFolder();
    }
    @GetMapping(value = "/addOrEditFileFolder")
    @ApiOperation("增加或修改文件夹")
    public FileFolder addOrEdit(@ApiParam(name = "fileFolderId", value = "folder id,如果是修改，此值不能为空") String fileFolderId){
        FileFolder fileFolder;
        if (StringUtils.isEmpty(fileFolderId) || "{fileFolderId}".equals(fileFolderId)) {
            fileFolder = new FileFolder();
            fileFolder.setCreateDate(new Date());
        } else {
            fileFolder = fileFolderService.getFileFolderById(Long.parseLong(fileFolderId));
        }
        return fileFolder;
    }

    @PostMapping(value = "/findFileFolderByName")
    @ApiOperation("通过文件夹中文件不分页")
    public List<FileFolder> findFileFolderName(@ApiParam(name = "name", value = "")  String name) {
        return fileFolderService.getFileFolderByName("%" + name +"%");
    }

    @PostMapping(value = "/saveFileFolder")
    @ApiOperation(value = "用户新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "fileFolder", required = true, value = "待保存的对象") @RequestBody FileFolder fileFolder) {
        try {
            FileFolder result = fileFolderService.saveOrUpdate(fileFolder);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save fileFolder data:" + JSON.toJSONString(result));
            }
            return new ResultInfo(true, "保存成功");
        } catch (Exception e) {
            LOGGER.error("保存用户失败：" + e);
            return new ResultInfo(false, "保存失败");
        }

    }

    @PostMapping("/delete/fileFolder/{fileFolderId}")
    @ApiOperation(value = "根据FileFolder ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "fileFolderId", required = true, value = "需要删除的FileFolder ID") @PathVariable String fileFolderId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            fileFolderService.removeFileFolder(Long.parseLong(fileFolderId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除FileFolder失败：id:" + fileFolderId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败，请检查是否有角色拥有此菜单");
        }
        return resultInfo;
    }

    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除FileFolder")
    public ResultInfo batchDeleteFileFolder(@ApiParam(name = "fileFolderIds", required = true, value = "需要删除的fileFolderId，多个fileFolderIds用英文逗号分隔") String fileFolderIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            String[] ids = fileFolderIds.split(",");
            Set<String> idStr = Sets.newHashSet(ids);
            Set<Long> idLong = idStr.stream().map(Long::parseLong).collect(Collectors.toSet());
            fileFolderService.batchRemoveFileFolder(idLong);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

}
