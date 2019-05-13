package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    @Query(value = "SELECT * FROM project WHERE id NOT IN (\n" +
            "SELECT project.id FROM project\n" +
            "INNER JOIN project_supervision_department psd\n" +
            "ON project.id = psd.project_id)", nativeQuery = true)
    List<Project> findProjectsByProjectSupervisionDepartmentIsNull();
}