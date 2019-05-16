package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.Conference;
import com.xinguan.workprocess.model.ConferenceSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConferenceSummaryRepository extends JpaRepository<ConferenceSummary, Long>, JpaSpecificationExecutor<ConferenceSummary> {
}
