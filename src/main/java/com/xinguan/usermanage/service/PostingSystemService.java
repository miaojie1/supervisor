package com.xinguan.usermanage.service;



import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.PostingSystem;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface PostingSystemService {
    PostingSystem getPostingSystemById(Long id);
    Page<PostingSystem> listExpPostingByPage(int pageSize, int pageNo);
    Page<PostingSystem> listPostingByPage(int pageSize, int pageNo,String postingName);
    PostingSystem addOrEditPosting(PostingSystem postingSystem, Employee announcer);
    void removePosting(Long id);
    void batchRemovePosting(Set<Long> ids);
}
