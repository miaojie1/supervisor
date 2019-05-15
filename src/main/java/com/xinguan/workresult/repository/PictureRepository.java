package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.Picture;
import com.xinguan.workresult.model.PictureFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long>, JpaSpecificationExecutor<Picture> {

    List<Picture> findByPictureFolder(PictureFolder pictureFolder);
}
