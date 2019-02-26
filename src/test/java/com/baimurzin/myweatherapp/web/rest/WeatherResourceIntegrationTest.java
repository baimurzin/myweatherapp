package com.baimurzin.myweatherapp.web.rest;

import com.baimurzin.myweatherapp.client.dto.Weather;
import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.client.dto.Wind;
import com.baimurzin.myweatherapp.client.weather.WeatherClient;
import com.baimurzin.myweatherapp.model.City;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@AutoConfigureWebTestClient
public class WeatherResourceIntegrationTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private WeatherClient weatherClient;

    public WeatherResourceIntegrationTest() {
    }

    @Test
    public void shouldReturnOkStatusWhenCityExists() {
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
        client
                .get()
                .uri("/weather/{cityId}", 707860L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.visibility").isEqualTo(weatherResponse.getVisibility());
    }

    @Test
    public void shouldReturnBadRequestWhenCityDoesntExists() {
        client
                .get()
                .uri("/weather/{cityId}", 1231234L)
                .exchange()
                .expectStatus().isBadRequest();
    }

}