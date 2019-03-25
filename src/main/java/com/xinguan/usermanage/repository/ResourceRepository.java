package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Menu, String> {

}
