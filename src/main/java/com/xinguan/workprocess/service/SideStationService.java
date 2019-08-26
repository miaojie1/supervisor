package com.xinguan.workprocess.service;

import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.SideStation;
import org.springframework.data.domain.Page;

public interface SideStationService {
    PageInfo<SideStation> listSideStations(int pageNo, int pageSize, String partName);

    SideStation saveSideStation(SideStation sideStation);

    void deleteSideStationById(long id);

    void checkSideStation(Long sideStationId, String taskId, Boolean approved, String auditOpinion);

}
