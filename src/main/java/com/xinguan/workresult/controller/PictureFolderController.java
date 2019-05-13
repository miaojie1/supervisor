package com.xinguan.workresult.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.workresult.model.PictureFolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api("影像资料文件夹相关接口")
@RestController
@RequestMapping("/pictureFolder")
public class PictureFolderController extends WorkResultBaseController {

    @ApiOperation(value = "获取影像文件夹列表", notes = "返回影像文件夹列表。支持通过文件夹名称模糊查询。")
    @PostMapping("/listPicFolderPage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<PictureFolder> listRolePage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                @ApiParam(name = "picFolderName", value = "角色名称，支持模糊查询") String picFolderName) {
        Page<PictureFolder> pictureFolders = pictureFolderService.listPictureFolderByPage(pageSize, pageNo, picFolderName);
        Map<String, Object> param = Maps.newHashMap("roleName", picFolderName);
        return new PageInfo<>(pictureFolders, param);
    }
}
