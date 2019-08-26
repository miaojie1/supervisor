package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.StudyMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long>, JpaSpecificationExecutor<StudyMaterial> {
    @Query(value = "select * from study_material where if(?1 !='',study_material_name like %?1%,1=1) and if(?2 !='',department_id = ?2,1=1) and if(?3 !='',study_material_category_id = ?3,1=1)",nativeQuery = true)
    Page<StudyMaterial> listStudyMaterial(String studyMaterialName, String departmentId, String studyMaterialCategoryId, Pageable pageable);
}
