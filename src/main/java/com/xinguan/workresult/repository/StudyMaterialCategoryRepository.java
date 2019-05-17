package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.StudyMaterialCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudyMaterialCategoryRepository extends JpaRepository<StudyMaterialCategory, Long>, JpaSpecificationExecutor<StudyMaterialCategory> {
}
