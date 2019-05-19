package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.TestPaperDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TestPaperDetailRepository extends JpaRepository<TestPaperDetail, Long>, JpaSpecificationExecutor<TestPaperDetail> {
    @Query(value = "select * from test_paper_detail where if(?1 !='',test_paper_detail_name like %?1%,1=1) and test_paper_id = ?2",nativeQuery = true)
    Page<TestPaperDetail> listTestPaperDetail (String testPaperDetailName, String testPaperId, Pageable pageable);
}
