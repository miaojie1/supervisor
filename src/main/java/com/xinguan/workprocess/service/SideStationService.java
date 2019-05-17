package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.SideStation;
import org.springframework.data.domain.Page;

public interface SideStationService {
    Page<SideStation> listSideStations(int pageNo, int pageSize, String partName);

    void saveSideStation(SideStation sideStation);

    void deleteSideStationById(long id);
}
