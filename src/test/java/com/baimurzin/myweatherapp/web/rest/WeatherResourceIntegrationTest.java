package com.baimurzin.myweatherapp.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@AutoConfigureWebTestClient
public class WeatherResourceIntegrationTest {

    @Autowired
    private WebTestClient client;

    public WeatherResourceIntegrationTest() {
    }

    @Test
    public void getWeatherByCityId() {
        client
                .get()
                .uri("/weather/{cityId}", 707860L)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getWeather() {
    }
}