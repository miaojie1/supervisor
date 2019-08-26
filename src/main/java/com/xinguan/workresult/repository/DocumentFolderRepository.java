package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.DocumentFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentFolderRepository extends JpaRepository<DocumentFolder, Long>, JpaSpecificationExecutor<DocumentFolder> {
}
