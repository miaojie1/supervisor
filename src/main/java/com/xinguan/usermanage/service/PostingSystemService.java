package com.xinguan.usermanage.service;



import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.PostingSystem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostingSystemService {
    PostingSystem getPostingSystemById(Long id);
    List<PostingSystem> listExpPostingByPage();
    Page<PostingSystem> listPostingByPage(int pageSize, int pageNo,String postingName);
    PostingSystem saveOrUpdate(PostingSystem postingSystem, Employee announcer,Long attachmentId);
    void removePosting(Long id);
    void removePostingBatch(String ids);
}
