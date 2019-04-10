package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByUsernameAndPassword(String username, String password);

    List<Employee> findAllByUsernameStartingWith(String startStr);

    Employee findByUsername(String username);

}
