package com.baimurzin.myweatherapp.client.weather;

import com.baimurzin.myweatherapp.client.BaseClient;
import com.baimurzin.myweatherapp.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseWeatherClient extends BaseClient {

    private final String apiKey;

    /**
     * Class constructor to build WebClient from  passed base URL
     *
     * @param baseUrl
     * @param apiKey
     * @see org.springframework.web.reactive.function.client.WebClient
     */
    public BaseWeatherClient(String baseUrl, String apiKey) {
        super(baseUrl);
        this.apiKey = apiKey;
        log.debug("WeatherClient was successfully created");
    }

    public Mono<ClientResponse> getWeatherByCity(City city, MediaType mediaTypeAccept) {
        return this.getClient("/data/2.5/weather", createQuery(city, apiKey), null, mediaTypeAccept);
    }

    private static Map<String, String> createQuery(City city, String apiKey) {
        Map<String, String> query = new HashMap<>();
        query.put("id", String.valueOf(city.getCityId()));
        query.put("APPID", apiKey);
        return query;
    }

}
