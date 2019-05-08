package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByParentMenuIsNull();

}
