package com.xinguan.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangzhan
 * @date 2019-04-08 15:39
 */
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    public static Map<String, Object> transforParamToMap(String param) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap = JSON.parseObject(param, Map.class);
        } catch (Exception e) {
            LOGGER.error("转换param失败：" + e);
        }
        return objectMap;
    }


    /**
     * 根据指定的表达式验证字符串是否合法
     *
     * @param regex 正则表达式
     * @param str   待验证的字符串
     * @return 是否合法
     */
    public static boolean verificationStr(String regex, String str) {
        if (StringUtils.isEmpty(regex)) {
            return false;
        }
        regex = regex.replaceAll("\\{[0-9a-zA-Z]*\\}", ".*");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
