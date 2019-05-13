package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.FileFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileFolderRepository extends JpaRepository<FileFolder, Long>, JpaSpecificationExecutor<FileFolder> {
}
