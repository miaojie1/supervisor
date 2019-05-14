package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.FileCategory;

import java.util.List;

public interface FileCategoryService {
    List<FileCategory> listAllCategory();

    FileCategory findCategoryById(Long categoryId);

    FileCategory findCategoryByName(String name);
}
