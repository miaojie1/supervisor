package com.xinguan.workprocess.service;

import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.CheckAcceptance;
import org.springframework.data.domain.Page;

public interface CheckAcceptanceService {
    PageInfo<CheckAcceptance> listCheckAcceptanceByPage(int pageSize, int pageNo, String acceptanceName);

    CheckAcceptance saveOrUpdate(CheckAcceptance checkAcceptance);

    void removeCheckAcceptance(Long id);

    void checkCheckAcceptance(Long checkAcceptanceId, String taskId, Boolean approved, String auditOpinion);
}
