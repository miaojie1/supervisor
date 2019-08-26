package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.BackUpData;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BackupDataService  {
    Page<BackUpData> listBackupDataByPage(int pageSize, int pageNo, String name);

}
