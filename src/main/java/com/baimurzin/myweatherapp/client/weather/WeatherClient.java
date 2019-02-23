package com.baimurzin.myweatherapp.client.weather;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.model.City;
import reactor.core.publisher.Mono;

/**
 *
 * @param <T>
 */
public interface WeatherClient<T extends WeatherResponse> {

    Mono<WeatherResponse> getWeatherByCity(City city);
}
