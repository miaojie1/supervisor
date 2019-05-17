package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.StudyMaterialCategory;
import com.xinguan.workresult.service.StudyMaterialCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyMaterialCategoryServiceImpl extends BaseService<StudyMaterialCategory> implements StudyMaterialCategoryService {
    @Override
    public List<StudyMaterialCategory> listAllStudyMaterialCategories(){
        return studyMaterialCategoryRepository.findAll();
    }
}
