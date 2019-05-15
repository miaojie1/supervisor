package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.OnlineSign;
import org.springframework.data.domain.Page;

public interface OnlineSignService {
    /**
     * 增加或者更新 在线会签
     * @param onlineSign
     */
    OnlineSign saveOnlineSign(OnlineSign onlineSign);

    /**
     * 删除 一次在线会签
     * @param id
     */
    void deleteOnlineSignById(Long id);

    /**
     * 根据部门获得 在线会签 分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<OnlineSign> listOnlineSignByDepart(int pageNo, int pageSize, String name);

    OnlineSign findById(long id);
}
