package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.ExaminationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ExaminationRecordRepository extends JpaRepository<ExaminationRecord, Long>, JpaSpecificationExecutor<ExaminationRecord> {
    @Modifying
    @Query(value = "update examination_record set is_submit =1, score =?1 where id=?2",nativeQuery = true)
    void updateExaminationRecord(int score,String examinationRecordId);

    @Query(value = "select * from examination_record where if(?1 !='',test_paper_id=?1,1=1) and is_submit = 1 and if(?2 !='',candidate_id = ?2,1=1)",nativeQuery = true)
    Page<ExaminationRecord> listExaminationRecordByPage(String testPaperId,String candidateId, Pageable pageable);
}
