package com.xinguan.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import com.xinguan.utils.CommonUtil;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangzhan
 * @date 2019-03-23 16:01
 */
@RestController
@RequestMapping("/menu")
@Api(value = "菜单相关接口 ")
public class MenuController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @PostMapping(value = "/listMenu")
    @ApiOperation(value = "获取当前登录用户的菜单")
    public Set<Menu> listMenuByCurrentUser() {
        Employee employee = employeeService.getCurrentUser();
        return menuService.listMenuByEmployee(employee);
    }

    @PostMapping(value = "/listMenuPage/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取资源列表")
    public PageInfo<Menu> listMenuPage(@ApiParam(name = "pageNo", required = true, value = "当前页，页数从1开始") @PathVariable("pageNo") int pageNo,
                                       @ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                       @ApiParam(name = "paramJson", value = "查询条件用json拼接，格式：{\"key1\":\"value1\",\"key2\":value2}") String paramJson) {
        Map<String, Object> param = CommonUtil.transforParamToMap(paramJson);
        Page<Menu> page = menuService.listMenuByPage(pageSize, pageNo, param);
        return new PageInfo<>(page, param);
    }

    @GetMapping(value = "/addOrEditMenu/menuId/{menuId}")
    @ApiOperation(value = "资源新增或修改GET方法")
    public Menu addOrEditMenu(@ApiParam(name = "menuId", value = "menu id,如果是修改，此值不能为空") @PathVariable String menuId) {
        Menu menu;
        if (StringUtils.isEmpty(menuId) || "{menuId}".equals(menuId)) {
            menu = new Menu();
        } else {
            menu = menuService.getMenuById(Long.parseLong(menuId));
        }
        return menu;
    }

    @PostMapping(value = "/saveMenu")
    @ApiOperation(value = "资源新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "menu", required = true, value = "待保存的对象") @RequestBody Menu menu) {
        Menu result = menuService.addOrEditMenu(menu);
        if (menu.getParentMenu() != null) {
            Menu parentMenu = menuService.getMenuById(menu.getParentMenu());
            if (parentMenu != null) {
                parentMenu.getSubMenus().add(result);
                menuService.addOrEditMenu(parentMenu);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("save menu data:" + JSON.toJSONString(result));
        }
        return new ResultInfo(true, "保存成功");
    }

    @PostMapping("/delete/menuId/{menuId}")
    @ApiOperation(value = "根据Menu ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "menuId", required = true, value = "需要删除的Menu ID") @PathVariable String menuId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            menuService.removeMenu(Long.parseLong(menuId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除Menu失败：id:" + menuId + ",errorMessage:" + e.getMessage());
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @PostMapping("/batch/delete/menu")
    @ApiOperation(value = "批量删除Menu")
    public ResultInfo batchDeleteMenu(@ApiParam(name = "menuIds", required = true, value = "需要删除的MenuId，多个MenuId用英文逗号分隔") String menuIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            String[] ids = menuIds.split(",");
            Set<String> idStr = Sets.newHashSet(ids);
            Set<Long> idLong = idStr.stream().map(Long::parseLong).collect(Collectors.toSet());
            menuService.batchRemoveMenu(idLong);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }


    public static void main(String[] args) {
        String str = "/menu/listMenuPage/pageNo/1/pageSize/10";
        String regex = "/menu/listMenuPage/pageNo/{pageNo}/pageSize/{pageSize}";
        System.out.println(CommonUtil.verificationStr(regex, str));
    }
}
