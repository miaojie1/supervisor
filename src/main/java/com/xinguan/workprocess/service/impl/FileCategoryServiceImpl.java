package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.FileCategory;
import com.xinguan.workprocess.service.FileCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileCategoryServiceImpl extends BaseService<FileCategory> implements FileCategoryService {
    @Override
    public List<FileCategory> listAllCategory() {
        return fileCategoryRepository.findAll();
    }

    @Override
    public FileCategory findCategoryById(Long categoryId) {
        return fileCategoryRepository.getOne(categoryId);
    }

    @Override
    public FileCategory findCategoryByName(String name) {
        return fileCategoryRepository.findAllByName(name);
    }
}
