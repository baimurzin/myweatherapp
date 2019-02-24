package com.baimurzin.myweatherapp.client.weather.impl;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.client.weather.BaseWeatherClient;
import com.baimurzin.myweatherapp.client.weather.WeatherClient;
import com.baimurzin.myweatherapp.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * The implementation of {@link WeatherClient}
 * for retrieving current weather data from openweatherapi
 *
 * @author Vladislav Baimurzin
 */
@Service
@Slf4j
public class OpenWeatherMapClient implements WeatherClient<WeatherResponse> {

    private final BaseWeatherClient weatherClient;

    public OpenWeatherMapClient(@Value("${api.client.openweathermap.url}") String url,
                                @Value("${api.client.openweathermap.API_KEY}") String apiKey) {
        this.weatherClient = new BaseWeatherClient(url, apiKey);
    }

    @Override
    public Mono<WeatherResponse> getWeatherByCity(City city) {
        log.debug("Retrieving weather data for city: {}", city);
        return weatherClient.getWeatherByCity(city, MediaType.APPLICATION_STREAM_JSON)
                .flatMap(res -> res.bodyToMono(WeatherResponse.class));
        //todo add extended class for different api call
    }
}
