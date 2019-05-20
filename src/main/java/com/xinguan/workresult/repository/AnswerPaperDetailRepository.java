package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.AnswerPaperDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AnswerPaperDetailRepository extends JpaRepository<AnswerPaperDetail, Long>, JpaSpecificationExecutor<AnswerPaperDetail> {
    @Query(value = "select * from answer_paper_detail where test_paper_detail_id = ?1 and if(?2 !='',respondent_id = ?2,1=1)",nativeQuery = true)
    Page<AnswerPaperDetail> listAnswerPaperDetail (String testPaperDetailId, String respondentId, Pageable pageable);
}