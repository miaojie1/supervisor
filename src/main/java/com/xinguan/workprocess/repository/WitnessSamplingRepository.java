package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.WitnessSampling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WitnessSamplingRepository extends JpaRepository<WitnessSampling, Long>, JpaSpecificationExecutor<WitnessSampling> {
}
