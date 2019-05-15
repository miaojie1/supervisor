package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.WitnessSampling;
import org.springframework.data.domain.Page;

public interface WitnessSamplingService {
    WitnessSampling saveWitnessSampling(WitnessSampling witnessSampling);
    void deleteWitnessSamplingById(Long id);
    Page<WitnessSampling> listWitnessSamplingByDepartment(int pageNo, int pageSize, String samplingName);
    WitnessSampling findById(int id);
}
