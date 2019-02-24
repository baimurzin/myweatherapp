package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.model.City;
import reactor.core.publisher.Flux;
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

    /**
     * Returns all objects of {@link WeatherResponse} for all
     * registered cities {@link City}
     *
     * @param cities list of cities which weather we need to retrieve
     * @return all entities
     */
    Flux<WeatherResponse> getWeatherForAllCities(Flux<City> cities);

    /**
     * Method allows to clear all data of registered city with cityId
     *
     * @param cityId ids of city that need to be deleted
     * @return Mono<Void>
     */
    Mono<Void> deleteWeatherData(Long cityId);
}
