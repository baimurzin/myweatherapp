package com.baimurzin.myweatherapp.service.impl;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.client.weather.WeatherClient;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;

    @Override
    public Mono<WeatherResponse> getWeather(City city) {
        return weatherClient.getWeatherByCity(city)
                .onErrorResume(err -> Flux.empty());
    }
}
