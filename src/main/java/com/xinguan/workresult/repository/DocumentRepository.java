package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.Document;
import com.xinguan.workresult.model.DocumentFolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    Page<Document> findByDocumentFolder(DocumentFolder documentFolder, Pageable pageable);
}
