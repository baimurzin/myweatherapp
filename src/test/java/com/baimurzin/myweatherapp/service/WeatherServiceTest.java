package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.client.dto.Weather;
import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.client.dto.Wind;
import com.baimurzin.myweatherapp.client.weather.WeatherClient;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.service.WeatherService;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class WeatherServiceTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private WeatherClient weatherClient;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void shouldReturnWeatherObjectFromMockedExternalAPI() {
        City city = new City(707860L , "Moscow");
        WeatherResponse weatherResponse = new WeatherResponse(
                new Wind(1.3),
                Arrays.asList(new Weather(1L, "smth", "Desc", "icon")),
                "base",
                123L,
                "Moscow",
                123L
        );

        Mockito.when(weatherClient.getWeatherByCity(Mockito.any(City.class)))
                .thenReturn(Mono.just(weatherResponse));

        StepVerifier
                .create(weatherService.getWeather(city))
                .expectNextMatches(wr -> wr.getVisibility().equals(weatherResponse.getVisibility()))
                .verifyComplete();
    }

    @Test
    public void shouldReturnWeatherObjectFromCache() {
        City city = new City(707860L , "Moscow");
        WeatherResponse weatherResponse = new WeatherResponse(
                new Wind(1.3),
                Arrays.asList(new Weather(1L, "smth", "Desc", "icon")),
                "base",
                12345L,
                "Moscow",
                123L
        );

        Mockito.when(weatherClient.getWeatherByCity(Mockito.any(City.class)))
                .thenReturn(Mono.just(weatherResponse));

        StepVerifier
                .create(weatherService.getWeather(city))
                //hardcoded...should be fixed. 123 Because this value taken from cache. testing caching
                .expectNextMatches(wr -> wr.getVisibility().equals(123L))
                .verifyComplete();
    }

    @Test
    public void shouldRemoveDataFromCache() {
        City city = new City(707860L , "Moscow");
        cacheManager.getCache("WEATHER_CACHE").put(city.getCityId(), new Object());
        assertNotNull(cacheManager.getCache("WEATHER_CACHE").get(city.getCityId()));
        weatherService.deleteWeatherData(city.getCityId()).subscribe();
        assertNull(cacheManager.getCache("WEATHER_CACHE").get(city.getCityId()));
    }
}