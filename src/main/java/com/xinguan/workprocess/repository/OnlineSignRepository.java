package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.OnlineSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OnlineSignRepository extends JpaRepository<OnlineSign, Long>, JpaSpecificationExecutor<OnlineSign> {
}
