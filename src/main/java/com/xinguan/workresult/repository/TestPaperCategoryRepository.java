package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.TestPaperCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestPaperCategoryRepository extends JpaRepository<TestPaperCategory, Long>, JpaSpecificationExecutor<TestPaperCategory> {
}
