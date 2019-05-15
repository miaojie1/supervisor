package com.xinguan.workprocess.service;

import com.xinguan.usermanage.model.Department;
import com.xinguan.workprocess.model.SiteAcceptance;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SiteAcceptanceService {
    /**
     * 分页获取进场验收列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<SiteAcceptance> listSiteAcceptancesByPage(int pageNo, int pageSize);

    /**
     * 增加或者更新 进场验收
     * @param siteAcceptance
     */
    SiteAcceptance saveSiteAcceptance(SiteAcceptance siteAcceptance);

    /**
     * 删除 一次进场验收
     * @param id
     */
    void deleteSiteAcceptById(Long id);

    /**
     * 根据部门获得 进场验收 分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<SiteAcceptance> listSiteAcceptancesByDepart(int pageNo, int pageSize);
}
