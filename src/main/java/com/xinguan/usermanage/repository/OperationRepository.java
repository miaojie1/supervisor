package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findOperationsByButtonId(String name);
}
