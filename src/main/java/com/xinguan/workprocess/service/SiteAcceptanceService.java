package com.xinguan.workprocess.service;

import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.SiteAcceptance;
import org.springframework.data.domain.Page;


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

    void checkSiteAccept(Long siteAcceptanceId, String taskId, Boolean approved, String auditOpinion);

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
    PageInfo<SiteAcceptance> listSiteAcceptancesByDepart(int pageNo, int pageSize, String name);

    SiteAcceptance findById(int id);
}
