package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.model.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    Mono<WeatherResponse> getWeatherForAllCities(City city);

    /**
     * Returns all objects of {@link WeatherResponse} for all
     * registered cities {@link City}
     *
     * @param cityList list of cities which weather we need to retrieve
     * @return all entities
     */
    Flux<WeatherResponse> getWeatherForAllCities(Flux<City> cities);
}
