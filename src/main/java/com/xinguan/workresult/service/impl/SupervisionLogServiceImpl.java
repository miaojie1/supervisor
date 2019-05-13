package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workresult.model.SupervisionLog;
import com.xinguan.workresult.service.SupervisionLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
public class SupervisionLogServiceImpl extends BaseService<SupervisionLog> implements SupervisionLogService {

    @Override
    public Page<SupervisionLog> listSupervisionLogByEmp(int pageNo, int pageSize, String name){
        return supervisionLogRepository.findAll((Specification<SupervisionLog>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(name)) {
                //路径名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("file_path").as(String.class), "%" + name + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<SupervisionLog> listSupervisionLogByPage(int pageNo, int pageSize){
        Pageable pageable = new PageRequest(pageNo,pageSize, Sort.Direction.ASC, "id");
        return supervisionLogRepository.findAll(pageable);
    }
}
