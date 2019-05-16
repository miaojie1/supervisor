package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.DocumentCategory;
import com.xinguan.workresult.service.DocumentCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentCategoryServiceImpl extends BaseService<DocumentCategory> implements DocumentCategoryService {
    @Override
    public List<DocumentCategory> listAllDocumentCategories(){
       return documentCategoryRepository.findAll();
   }

    @Override
    public DocumentCategory findDocuCateById(Long id){
        return documentCategoryRepository.findById(id).get();
    }
}
