package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import com.xinguan.workprocess.service.KnowledgeService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KnowledgeServiceImpl extends BaseService<Knowledge> implements KnowledgeService {
    @Override
    public List<Knowledge> listAllKnowledge(FileFolder folder) {
        return knowledgeRepository.findAllByFileFolder(folder);
    }

    @Override
    public Knowledge getKnowledgeById(Long id) {
        return knowledgeRepository.getOne(id);
    }

    @Override
    public Knowledge saveOrUpdate(Knowledge knowledge) {
        Example<Knowledge> knowledgeExample = getSimpleExample(knowledge);
        if (knowledgeRepository.exists(knowledgeExample)) {
            Assert.notNull(knowledge.getFileName(), "文件已存在!");
        } else {
            knowledge.setCreateDate(new Date());
        }
        return knowledgeRepository.saveAndFlush(knowledge);
    }

    @Override
    public void removeKnowledge(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        knowledgeRepository.deleteById(id);
    }

    @Override
    public void batchRemoveKnowledge(Set<Long> ids) {
        Set<Knowledge> knowledges = ids.stream().map(id -> getKnowledgeById(id)).collect(Collectors.toSet());
        knowledgeRepository.deleteInBatch(knowledges);
    }
}
