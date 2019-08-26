package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>, JpaSpecificationExecutor<Knowledge> {
    List<Knowledge> findAllByFileFolder(FileFolder fileFolder);

    List<Knowledge> findByFileNameLike(String fileName);

    @Query(value = "select * from knowledge where file_name like %?1% and file_folder_id = ?2",nativeQuery = true)
    List<Knowledge> findByFileNameAndFileFolderLike(String fileName, String fileFolderId);
}
