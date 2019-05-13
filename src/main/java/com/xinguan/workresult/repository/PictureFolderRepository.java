package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.PictureFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PictureFolderRepository extends JpaRepository<PictureFolder, Long>, JpaSpecificationExecutor<PictureFolder> {
}
