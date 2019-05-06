package com.xinguan.usermanage.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xinguan.usermanage.model.Role;

import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;
import org.assertj.core.util.Maps;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@Api(value = "角色相关接口")
public class RoleController extends BaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    @ApiOperation(value = "获取角色列表", notes = "返回角色列表。支持通过角色名称模糊查询。")
    @PostMapping("/listRolePage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<Role> listRolePage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                   @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                   @ApiParam(name = "roleName", value = "角色名称，支持模糊查询") String roleName) {
        Page<Role> roles = roleService.listRoleByPage(pageSize, pageNo, roleName);
        Map<String, Object> param = Maps.newHashMap("roleName", roleName);
        return new PageInfo<>(roles, param);
    }

    @PostMapping(value = "/saveRole")
    @ApiOperation(value = "角色新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "role", required = true, value = "待保存的对象") @RequestBody Role role) {
        try{
            Role result = roleService.addOrEditRole(role);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("save menu data:" + JSON.toJSONString(result));
            return new ResultInfo(true, "保存成功");
        }catch (Exception e) {
            LOGGER.error("保存公告失败：" + e);
            return new ResultInfo(false, "保存失败");
        }
    }


    @PostMapping("/delete/roleId/{roleId}")
    @ApiOperation(value = "根据Role ID删除 角色")
    public ResultInfo deleteById(@ApiParam(name = "roleId", required = true, value = "需要删除的role ID") @PathVariable String roleId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            roleService.removeRole(Long.parseLong(roleId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除Role失败：id:" + roleId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除Role")
    public ResultInfo batchDeletePosting(@ApiParam(name = "roleIds", required = true, value = "需要删除的RoleId，多个RoleId用英文逗号分隔") String roleIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            String[] ids = roleIds.split(",");
            Set<String> idStr = Sets.newHashSet(ids);
            Set<Long> idLong = idStr.stream().map(Long::parseLong).collect(Collectors.toSet());
            roleService.batchRemoveRole(idLong);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
