package com.xinguan.workprocess.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.Conference;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ConferenceService {

    PageInfo<Conference> listConferenceByPage(int pageSize, int pageNo, String content);

    Conference saveOrUpdate(Conference conference);

    void removeConference(Long id);

    void batchRemoveConference(Set<Long> ids);

    Conference findById(long id);

    Conference allotWriter(long id, List<Employee> employees);

    void writeSummary(Long conferenceId, String taskId, String content);

    void checkConference(Long conferenceId, String taskId, Boolean approved, String auditOpinion);
}
