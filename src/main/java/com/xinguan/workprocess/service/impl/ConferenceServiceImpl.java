package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Conference;
import com.xinguan.workprocess.model.FileCategory;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import com.xinguan.workprocess.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ConferenceServiceImpl extends BaseService<Conference> implements ConferenceService {

    @Autowired
    EmployeeService employeeService;
    @Override
    public Page<Conference> listConferenceByPage(int pageSize, int pageNo, String content) {
        Conference conference = new Conference();
        Employee employee = employeeService.getCurrentUser();
        Department department = employee.getDepartment();
        conference.setDepartment(department);
        Example<Conference> example;
        if (content != null) {
            conference.setContent("%" + content + "%");
        }
        example = getSimpleExample(conference);
        return conferenceRepository.findAll(example, PageRequest.of(pageNo, pageSize));

    }

    @Override
    public Conference saveOrUpdate(Conference conference) {
        Example<Conference> conferenceExample = getSimpleExample(conference);
        if (conferenceRepository.exists(conferenceExample)) {
            conference.setModificationDate(new Date());
        } else {
            conference.setInitiator(employeeService.getCurrentUser());
            conference.setDepartment(employeeService.getCurrentUser().getDepartment());
            if(employeeService.getCurrentUser().getDepartmentPosition()!=null){
                conference.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
            }
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
