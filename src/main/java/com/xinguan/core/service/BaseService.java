package com.xinguan.core.service;

import com.xinguan.usermanage.repository.*;
import com.xinguan.workprocess.repository.*;
import com.xinguan.workresult.repository.PictureFolderRepository;
import com.xinguan.workresult.repository.PictureRepository;
import com.xinguan.workresult.repository.SupervisionLogRepository;
import com.xinguan.workresult.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author zhangzhan
 * @date 2019-04-05 14:29
 */
@Component
public abstract class BaseService<T> {

    @Autowired
    protected EmployeeRepository employeeRepository;
    @Autowired
    protected MenuRepository menuRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected DepartmentRepository departmentRepository;
    @Autowired
    protected PostingSystemRepository postingSystemRepository;
    @Autowired
    protected AttachmentRepository attachmentRepository;
    @Autowired
    protected  EmployeeStatusRepository employeeStatusRepository;
    @Autowired
    protected DepartmentPositionRepository departmentPositionRepository;
    @Autowired
    protected ProjectRepository projectRepository;
    @Autowired
    protected ProjectSupervisionDepartmentRepository projectSupervisionDepartmentRepository;
    @Autowired
    protected ProjectStatusRepository projectStatusRepository;
    @Autowired
    protected OperationRepository operationRepository;
    @Autowired
    protected KnowledgeRepository knowledgeRepository;
    @Autowired
    protected FileFolderRepository fileFolderRepository;
    @Autowired
    protected PictureRepository pictureRepository;
    @Autowired
    protected PictureFolderRepository pictureFolderRepository;
    @Autowired
    protected BackUpDataRepository backUpDataRepository;
    @Autowired
    protected SupervisionLogRepository supervisionLogRepository;
    @Autowired
    protected DocumentFolderRepository documentFolderRepository;
    @Autowired
    protected DocumentRepository documentRepository;
    @Autowired
    protected DocumentCategoryRepository documentCategoryRepository;
    @Autowired
    protected SupervisionLogRecordRepository supervisionLogRecordRepository;
    @Autowired
    protected FileCategoryRepository fileCategoryRepository;
    @Autowired
    protected SiteAcceptanceRepository siteAcceptanceRepository;
    @Autowired
    protected ConferenceRepository conferenceRepository;
    @Autowired
    protected OnlineSignRepository onlineSignRepository;
    @Autowired
    protected SideStationRepository sideStationRepository;
    @Autowired
    protected WitnessSamplingRepository witnessSamplingRepository;
    @Autowired
    protected PatrolRepository patrolRepository;
    @Autowired
    protected ConferenceSummaryRepository conferenceSummaryRepository;
    @Autowired
    protected CheckAcceptanceRepository checkAcceptanceRepository;

    public BaseService() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);


    /**
     * 获取简单匹配器
     * 1.字符串类型的属性支持模糊查询
     * 2.忽略大小写
     * 3.空值忽略
     * 4.version属性忽略
     *
     * @return
     */
    protected ExampleMatcher getSimpleExampleMatcher() {
        return ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true)
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withIgnorePaths("version");
    }

    protected Example<T> getSimpleExample(T t) {
        ExampleMatcher exampleMatcher = getSimpleExampleMatcher();
        return Example.of(t, exampleMatcher);
    }

    protected T transforObject(T o, Map<String, Object> params) {
        Class<?> tClass = o.getClass();
        Field[] fields = tClass.getDeclaredFields();
        Method[] methods = tClass.getMethods();
        for (Field field : fields) {
            try {
                Object fieldValue = getParam(field, params);
                Method method = getMethodByParam(field, methods);
                if (fieldValue != null && method != null) {
                    method.invoke(o, fieldValue);
                }
            } catch (Exception e) {
                LOGGER.error("转换查询参数失败" + e.getMessage());
            }
        }

        return o;
    }

    /**
     * 根据字段名获取对应的Set方法
     *
     * @param field
     * @param methods
     * @return
     */
    private Method getMethodByParam(Field field, Method[] methods) {
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase("set" + field.getName())) {
                return method;
            }
        }
        return null;
    }

    /**
     * 根据字段名称获取参数值
     *
     * @param field
     * @param params
     * @return
     */
    private Object getParam(Field field, Map<String, Object> params) {
        if (params.containsKey(field.getName())) {
            return params.get(field.getName());
        }
        return null;
    }

}
