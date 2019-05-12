package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Operation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OperationService {
    List<Operation> findOperationByButtonId(String name);
}
