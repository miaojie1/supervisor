package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.SideStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SideStationRepository extends JpaRepository<SideStation, Long>, JpaSpecificationExecutor<SideStation> {
}
