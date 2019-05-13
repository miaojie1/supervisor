package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PictureRepository extends JpaRepository<Picture, Long>, JpaSpecificationExecutor<Picture> {
}
