package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.service.HttpClient;
import org.jolokia.util.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping
public class WeatherController {
    @Autowired
    HttpClient httpClient;

    @PostMapping(value = "/getWeatherList")
    public String getWeatherList(@RequestParam String cityId){
        String http = "http://t.weather.sojson.com/api/weather/city/" + cityId;
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        return httpClient.clint(http, method, multiValueMap);
    }
}
