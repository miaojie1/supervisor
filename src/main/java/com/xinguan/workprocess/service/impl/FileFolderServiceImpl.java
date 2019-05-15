package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.service.FileFolderService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileFolderServiceImpl extends BaseService<FileFolder> implements FileFolderService {
    @Override
    public List<FileFolder> listAllFileFolder() {
        return fileFolderRepository.findAll();
    }

    @Override
    public FileFolder getFileFolderById(Long id) {
        return fileFolderRepository.getOne(id);
    }

    @Override
    public List<FileFolder> getFileFolderByName(String name) {
        return fileFolderRepository.findByNameLike(name);
    }

    @Override
    public FileFolder saveOrUpdate(FileFolder fileFolder) {
        Example<FileFolder> employeeExample = getSimpleExample(fileFolder);
        if (fileFolderRepository.exists(employeeExample)) {
            Assert.notNull(fileFolder.getName(), "文件夹已存在!");
        } else {
            fileFolder.setCreateDate(new Date());
        }
        return fileFolderRepository.saveAndFlush(fileFolder);
    }

    @Override
    public void removeFileFolder(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        fileFolderRepository.deleteById(id);
    }

    @Override
    public void batchRemoveFileFolder(Set<Long> ids) {
        Set<FileFolder> fileFolders = ids.stream().map(id -> getFileFolderById(id)).collect(Collectors.toSet());
        fileFolderRepository.deleteInBatch(fileFolders);
    }
}
