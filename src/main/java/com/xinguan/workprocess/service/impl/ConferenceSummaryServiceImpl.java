package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.Conference;
import com.xinguan.workprocess.model.ConferenceSummary;
import com.xinguan.workprocess.service.ConferenceSummaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceSummaryServiceImpl extends BaseService<ConferenceSummary> implements ConferenceSummaryService {

    @Override
    public List<ConferenceSummary> listAllConferenceSummary() {
        return conferenceSummaryRepository.findAll();
    }
}
