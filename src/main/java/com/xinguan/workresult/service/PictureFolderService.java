package com.xinguan.workresult.service;

import com.xinguan.workresult.model.PictureFolder;
import org.springframework.data.domain.Page;

public interface PictureFolderService {

    Page<PictureFolder> listPictureFolderByPage(int pageSize, int pageNo, String picFolderName);
}
