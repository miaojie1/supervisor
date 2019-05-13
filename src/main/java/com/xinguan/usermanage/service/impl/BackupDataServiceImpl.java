package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.BackUpData;
import com.xinguan.usermanage.service.BackupDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
public class BackupDataServiceImpl extends BaseService<BackUpData> implements BackupDataService {
    @Override
    public Page<BackUpData> listBackupDataByPage(int pageSize, int pageNo, String name){
        return backUpDataRepository.findAll((Specification<BackUpData>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(name)) {
                //路径名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("file_path").as(String.class), "%" + name + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "date"));
//        BackUpData backUpData = new BackUpData();
//        Example<BackUpData> example = getSimpleExample(backUpData);
//        return backUpDataRepository.findAll(example, PageRequest.of(pageNo, pageSize));
    }
}
