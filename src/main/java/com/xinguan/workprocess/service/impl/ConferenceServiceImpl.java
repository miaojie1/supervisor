package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Role;
import com.xinguan.workprocess.model.Conference;
import com.xinguan.workprocess.model.FileCategory;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import com.xinguan.workprocess.service.ConferenceService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Set;

@Service
public class ConferenceServiceImpl extends BaseService<Conference> implements ConferenceService {

    @Override
    public Page<Conference> listConferenceByPage(int pageNo, int pageSize, String content, Employee employee) {
        Conference conference = new Conference();
        Example<Conference> example;

        if(employee.getDepartment().getName() == conference.getInitiator().getDepartment().getName() ){
            if (content != null) {
                //transforObject(employee, params);
                conference.setContent("%" + content + "%");
                //example = Example.of(employee);
            }
            example = getSimpleExample(conference);
            return conferenceRepository.findAll(example, PageRequest.of(pageNo, pageSize));
        }
        return null;

    }

    @Override
    public Conference saveOrUpdate(Conference conference) {
        Example<Conference> conferenceExample = getSimpleExample(conference);
        if (conferenceRepository.exists(conferenceExample)) {
            conference.setModificationDate(new Date());
        } else {
            conference.setCreateDate(new Date());
        }
        return conferenceRepository.saveAndFlush(conference);
    }

    @Override
    public void removeConference(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        conferenceRepository.deleteById(id);
    }

    @Override
    public void batchRemoveConference(Set<Long> ids) {
    }
}
