package com.xinguan.workresult.service;

import com.xinguan.workresult.model.DocumentCategory;

import java.util.List;

public interface DocumentCategoryService {
    List<DocumentCategory> listAllDocumentCategories();
    DocumentCategory findDocuCateById(Long id);
}
