package com.xinguan.repository;

import com.xinguan.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Employee findByUsernameAndPassword(String username, String password);

    List<Employee> findAllByUsernameStartingWith(String startStr);

    Employee findByUsername(String username);

}
