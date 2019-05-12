package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;

import java.util.List;
import java.util.Set;

public interface KnowledgeService {
    List<Knowledge> listAllKnowledge(FileFolder fileFolder);

    Knowledge getKnowledgeById(Long id);

    Knowledge saveOrUpdate(Knowledge knowledge);

    void removeKnowledge(Long id);

    void batchRemoveKnowledge(Set<Long> ids);
}
