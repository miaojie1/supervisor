package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

    @Query(value = "select * from document where if(?1 !='',document_name like %?1%,1=1) and document_folder_id = ?2 and if(?3 !='',document_category_id = ?3,1=1)",nativeQuery = true)
    Page<Document> findByDocumentFolderAndDocumentCategoryAndDocumentName(String documentName, String documentFolderId, String documentCategoryId,Pageable pageable);
}
