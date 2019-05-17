package com.xinguan.workresult.repository;

import com.xinguan.workresult.model.AccountCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountCategoryRepository extends JpaRepository<AccountCategory, Long>, JpaSpecificationExecutor<AccountCategory> {
    AccountCategory findAccountCategoryByName(String name);
}
