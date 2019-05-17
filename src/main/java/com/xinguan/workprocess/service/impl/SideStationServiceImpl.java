package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.SideStation;
import com.xinguan.workprocess.service.SideStationService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SideStationServiceImpl extends BaseService<SideStation> implements SideStationService {
    @Override
    public Page<SideStation> listSideStations(int pageNo, int pageSize, String partName) {
        SideStation sideStation=new SideStation();
        if (partName!=""){
            sideStation.setPart("%" + partName + "%");
        }
        Example<SideStation> sideStationExample = getSimpleExample(sideStation);
        return sideStationRepository.findAll(sideStationExample, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public void saveSideStation(SideStation sideStation) {
        sideStationRepository.saveAndFlush(sideStation);
    }

    @Override
    public void deleteSideStationById(long id) {
        sideStationRepository.deleteById(id);
    }

}
