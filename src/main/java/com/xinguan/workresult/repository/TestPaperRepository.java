package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.TestPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TestPaperRepository extends JpaRepository<TestPaper, Long>, JpaSpecificationExecutor<TestPaper> {
    @Query(value = "select * from test_paper where if(?1 !='',test_paper_name like %?1%,1=1) and if(?2 !='',department_id = ?2,1=1) and if(?3 !='',test_paper_category_id = ?3,1=1)",nativeQuery = true)
    Page<TestPaper> listTestPaper(String testPaperName, String departmentId, String testPaperCategoryId, Pageable pageable);
}
