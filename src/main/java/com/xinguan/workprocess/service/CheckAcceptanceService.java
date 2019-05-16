package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.CheckAcceptance;
import org.springframework.data.domain.Page;

public interface CheckAcceptanceService {
    Page<CheckAcceptance> listCheckAcceptanceByPage(int pageSize, int pageNo, String acceptanceName);

    CheckAcceptance saveOrUpdate(CheckAcceptance checkAcceptance);

    void removeCheckAcceptance(Long id);
}
