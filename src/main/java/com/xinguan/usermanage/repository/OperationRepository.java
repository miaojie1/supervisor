package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
