package com.xinguan.workprocess.service;

import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.WitnessSampling;

public interface WitnessSamplingService {
    WitnessSampling saveWitnessSampling(WitnessSampling witnessSampling);
    void checkWitnessSampling(Long witnessSamplingId, String taskId, Boolean approved, String auditOpinion);
    void deleteWitnessSamplingById(Long id);
    PageInfo<WitnessSampling> listWitnessSamplingByDepartment(int pageNo, int pageSize, String samplingName);
    WitnessSampling findById(int id);
}
