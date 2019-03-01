package com.baimurzin.myweatherapp.client.weather;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.model.City;
import reactor.core.publisher.Mono;

/**
 * Weather client that describes method to access API
 *
 * @param <T>
 */
public interface WeatherClient<T extends WeatherResponse> {

    Mono<WeatherResponse> getWeatherByCity(City city);
}
