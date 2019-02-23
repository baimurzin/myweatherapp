package com.baimurzin.myweatherapp.web.rest;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.client.weather.WeatherClient;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WeatherResource {

    private final WeatherService weatherService;

    @GetMapping(value = "/weather/{cityId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<WeatherResponse> getWeatherByCityId(@PathVariable Long cityId) {
        City city = new City();
        city.setCityId(cityId);
        return weatherService.getWeather(city);
    }
}
