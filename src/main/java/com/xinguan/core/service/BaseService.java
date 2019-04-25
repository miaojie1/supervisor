package com.xinguan.core.service;

import com.xinguan.usermanage.repository.DepartmentRepository;
import com.xinguan.usermanage.repository.EmployeeRepository;
import com.xinguan.usermanage.repository.MenuRepository;
import com.xinguan.usermanage.repository.RoleRepository;
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