package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.FileFolder;

import java.util.List;
import java.util.Set;

public interface FileFolderService {

    List<FileFolder> listAllFileFolder();

    FileFolder getFileFolderById(Long id);

    FileFolder saveOrUpdate(FileFolder fileFolder);

    void removeFileFolder(Long id);

    void batchRemoveFileFolder(Set<Long> ids);
}
