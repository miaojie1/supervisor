package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.repository.RoleRepository;
import com.xinguan.usermanage.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public List<Role> listAllRoles() {
        return roleRepository.findAll();
    }

    //查詢角色信息
    @Override
    public Page<Role> listRoleByPage(int pageSize, int pageNo, String roleName) {
        return roleRepository.findAll((Specification<Role>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(roleName)) {
                //公告名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + roleName + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }
    //增加或修改角色信息
    @Transactional
    @Override
    public Role addOrEditRole(Role role){
        Example<Role> roleExample = getSimpleExample(role);
        if (roleRepository.exists(roleExample))
            role.setModificationDate(new Date());
        else
            role.setCreateDate(new Date());
        return roleRepository.saveAndFlush(role);
    }
    //刪除角色信息
    @Transactional
    @Override
    public void removeRole(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        roleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void batchRemoveRole(Set<Long> ids) {
        Set<Role> roles = ids.stream().map(id -> getRoleById(id)).collect(Collectors.toSet());
        roleRepository.deleteInBatch(roles);
    }
}
