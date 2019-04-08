package com.xinguan.usermanage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

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
