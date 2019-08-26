package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.FileCategory;
import com.xinguan.workprocess.model.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileCategoryRepository extends JpaRepository<FileCategory, Long>, JpaSpecificationExecutor<FileCategory> {
    FileCategory findAllByName(String name);
}
