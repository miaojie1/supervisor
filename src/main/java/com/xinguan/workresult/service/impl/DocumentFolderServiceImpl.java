package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.DocumentFolder;
import com.xinguan.workresult.service.DocumentFolderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.Date;

@Service
public class DocumentFolderServiceImpl extends BaseService<DocumentFolder> implements DocumentFolderService {
    @Override
    public Page<DocumentFolder> listFolderByPage(int pageSize, int pageNo, String folderName) {
        return documentFolderRepository.findAll((Specification<DocumentFolder>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(folderName)) {
                //文档库名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + folderName + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }
    @Transactional
    @Override
    public DocumentFolder saveOrUpdate(DocumentFolder documentFolder){
        Example<DocumentFolder> documentFolderExample = getSimpleExample(documentFolder);
        if (!documentFolderRepository.exists(documentFolderExample)){
            documentFolder.setCreateDate(new Date());
        }
        return documentFolderRepository.saveAndFlush(documentFolder);
    }

    @Transactional
    @Override
    public void removeDocumentFolder(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        documentFolderRepository.deleteById(id);
    }
}
