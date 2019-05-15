package com.xinguan.workprocess.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.workprocess.model.Conference;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ConferenceService {

    Page<Conference> listConferenceByPage(int pageSize, int pageNo, String content, Employee employee);

    Conference saveOrUpdate(Conference conference);

    void removeConference(Long id);

    void batchRemoveConference(Set<Long> ids);
}
