package com.xinguan.workresult.service;

import com.xinguan.workresult.model.AccountCategory;

public interface AccountCategoryService {
    AccountCategory saveAccCategory(AccountCategory accountCategory);
    void deleteAccCategoryById(Long id);
    AccountCategory getAccCategoryByName(String name);
}
