package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.ParallelTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ParallelTestRepository extends JpaRepository<ParallelTest, Long>, JpaSpecificationExecutor<ParallelTest> {
}
