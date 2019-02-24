package com.baimurzin.myweatherapp.client.weather;

import com.baimurzin.myweatherapp.client.BaseClient;
import com.baimurzin.myweatherapp.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * The extended class that used for retrieving data from OpenWeatherMap
 *
 * @author Vladislav Baimurzin
 * @see com.baimurzin.myweatherapp.client.weather.impl.OpenWeatherMapClient
 */
@Slf4j
public class BaseWeatherClient extends BaseClient {

    /**
     * Api key to access external API
     */
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

    /**
     * Call to get current weather for passed city
     *
     * @param city city
     * @param mediaTypeAccept media type need to configure client
     * @return Mono of ClientResponse
     */
    public Mono<ClientResponse> getWeatherByCity(City city, MediaType mediaTypeAccept) {
        return this.getClient("/data/2.5/weather", createQuery(city, apiKey), null, mediaTypeAccept);
    }

    /**
     * Building query from passed parameters
     *
     * @param city a {@link City} object
     * @param apiKey api key that used for accessing external api endpoints
     * @return map<key, value> of parameters
     */
    private static Map<String, String> createQuery(City city, String apiKey) {
        Map<String, String> query = new HashMap<>();
        query.put("id", String.valueOf(city.getCityId()));
        query.put("APPID", apiKey);
        return query;
    }

}
