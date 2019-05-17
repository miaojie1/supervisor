package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.AccountCategory;
import com.xinguan.workresult.service.AccountCategoryService;
import org.springframework.stereotype.Service;

@Service
public class AccountCategoryServiceImpl extends BaseService<AccountCategory> implements AccountCategoryService {

    @Override
    public AccountCategory saveAccCategory(AccountCategory accountCategory){
        return accountCategoryRepository.saveAndFlush(accountCategory);
    }
    @Override
    public void deleteAccCategoryById(Long id){
        accountCategoryRepository.deleteById(id);
    }
    @Override
    public AccountCategory getAccCategoryByName(String name){
        return accountCategoryRepository.findAccountCategoryByName(name);
    }
}
