package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.model.City;
import reactor.core.publisher.Mono;

/**
 * The service provides methods and abstraction to manage
 * Weather resources. That can produces {@link Mono} or {@link reactor.core.publisher.Flux}
 * as a result of method execution
 *
 * @author Vladislav Baimurzin
 */
public interface WeatherService {

    /**
     * Method to load weather data for certain city
     * Result could be cached
     *
     * @param city a City object
     * @return Mono of {@link WeatherResponse}
     */
    Mono<WeatherResponse> getWeather(City city);
}
