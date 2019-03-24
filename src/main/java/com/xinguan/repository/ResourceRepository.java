package com.xinguan.repository;

import com.xinguan.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Menu, String> {

}
