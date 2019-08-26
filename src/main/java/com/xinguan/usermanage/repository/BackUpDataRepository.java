package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.BackUpData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BackUpDataRepository extends JpaRepository<BackUpData, Long>, JpaSpecificationExecutor<BackUpData> {

}
