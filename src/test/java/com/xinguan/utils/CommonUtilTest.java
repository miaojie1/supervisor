package com.xinguan.utils;

import com.xinguan.service.BaseServiceTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class CommonUtilTest extends BaseServiceTest {

    @Test(dataProvider = "transforParamToMap")
    public void testTransforParamToMap(String o) {
        Map<String, Object> map = CommonUtil.transforParamToMap(o);
        System.out.println(map);
    }

    @DataProvider(name = "transforParamToMap")
    public Object[] dataProvider() {
        return new String[]{null, "", " ", "23234", "param", "{}", "{name:zhang}", "{\"name\":\"zhang\"}"};
    }
}