package com.baimurzin.myweatherapp.web.rest;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.service.CityService;
import com.baimurzin.myweatherapp.service.WeatherService;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for view and managing Weather data
 *
 * @author Vladislav Baimurzin
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class WeatherResource {

    private final WeatherService weatherService;
    private final CityService cityService;

    /**
     * GET /weather/:cityId : get the weather by city id
     *
     * @param cityId the id of {@link City}
     * @return The {@link WeatherResponse} data for certain city
     */
    @GetMapping(value = "/weather/{cityId}", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Mono<WeatherResponse> getWeatherByCityId(@PathVariable Long cityId) {
        //if city not registered, we register it if such city valid
        //then we will find the weather for it
        return cityService.findById(cityId)
                .flatMap(city -> {
                    if (!city.isPresent()) {
                        log.debug("No such city registered. Trying to register it...");
                        return cityService.add(new CityDTO(cityId));
                    } else {
                        log.debug("City found in database. Trying to get weather for it...");
                        return Mono.just(city.get());
                    }
                })
                .flatMap(weatherService::getWeather);
    }

    /**
     * GET /weather : get all weather for registered cities
     *
     * @return the Flux of {@link WeatherResponse}
     */
    @GetMapping(value = "/weather", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<WeatherResponse> getWeather() {
        return weatherService.getWeatherForAllCities(cityService.findAll());
    }

}
