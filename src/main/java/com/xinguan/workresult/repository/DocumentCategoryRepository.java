package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.DocumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentCategoryRepository extends JpaRepository<DocumentCategory, Long>, JpaSpecificationExecutor<DocumentCategory> {
}
