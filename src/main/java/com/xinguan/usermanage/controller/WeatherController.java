package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.service.HttpClient;
import org.jolokia.util.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WeatherController {
    @Autowired
    HttpClient httpClient;

    @PostMapping(value = "/getWeatherList")
    public String getWeatherList(@RequestParam String cityId){
        //String http = "http://t.weather.sojson.com/api/weather/city/" + cityId;
        String http = "http://t.weather.itboy.net/api/weather/city/" + cityId;
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        return httpClient.clint(http, method, multiValueMap);
    }
}
