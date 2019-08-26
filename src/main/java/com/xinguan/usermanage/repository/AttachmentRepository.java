package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> , JpaSpecificationExecutor<Attachment> {
    @Modifying
    @Query(value = "delete from posting_system_attachment where attachments_id=?1",nativeQuery = true)
    void deletePostingSystemAttachmentById(Long attachmentId);
}
