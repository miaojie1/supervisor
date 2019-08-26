package com.xinguan.usermanage.repository;

import com.xinguan.usermanage.model.PostingSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostingSystemRepository extends JpaRepository<PostingSystem, Long>, JpaSpecificationExecutor<PostingSystem> {

}
