package com.xinguan.workresult.controller;

import com.alibaba.fastjson.JSON;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.PictureFolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api("影像资料文件夹相关接口")
@RestController
@RequestMapping("/pictureFolder")
public class PictureFolderController extends WorkResultBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PictureFolderController.class);

    @ApiOperation(value = "获取影像文件夹列表", notes = "返回影像文件夹列表。支持通过文件夹名称模糊查询。")
    @PostMapping("/listPicFolderPage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<PictureFolder> listRolePage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                @ApiParam(name = "pictureFolderName", value = "角色名称，支持模糊查询") String pictureFolderName) {
        Page<PictureFolder> pictureFolders = pictureFolderService.listPictureFolderByPage(pageSize, pageNo, pictureFolderName);
        Map<String, Object> param = Maps.newHashMap("pictureFolderName", pictureFolderName);
        return new PageInfo<>(pictureFolders, param);
    }

    @PostMapping(value = "/savePictureFolder")
    @ApiOperation(value = "影像文件夹新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "pictureFolder", required = true, value = "待保存的对象") @RequestBody PictureFolder pictureFolder) {
        try {
            PictureFolder result = pictureFolderService.saveOrUpdate(pictureFolder);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save pictureFolder data:" + JSON.toJSONString(result));
            }
            return new ResultInfo(true, "保存成功");
        } catch (Exception e) {
            LOGGER.error("保存用户失败：" + e);
            return new ResultInfo(false, "保存失败");
        }

    }

    @PostMapping("/delete/pictureFolder/{pictureFolderId}")
    @ApiOperation(value = "根据PictureFolder ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "pictureFolderId", required = true, value = "需要删除的PictureFolder ID") @PathVariable String pictureFolderId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            pictureFolderService.removePictureFolderById(Long.parseLong(pictureFolderId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除PictureFolder失败：id:" + pictureFolderId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
