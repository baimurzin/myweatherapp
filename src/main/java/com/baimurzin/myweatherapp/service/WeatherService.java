package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.model.City;
import reactor.core.publisher.Mono;

public interface WeatherService {

    Mono<WeatherResponse> getWeather(City city);
}
