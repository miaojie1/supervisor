package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.AnswerPaperDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnswerPaperDetailRepository extends JpaRepository<AnswerPaperDetail, Long>, JpaSpecificationExecutor<AnswerPaperDetail> {
}
