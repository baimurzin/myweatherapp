package com.baimurzin.myweatherapp.web.rest;

import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@AutoConfigureWebTestClient
public class CityResourceIntegrationTest {

    @Autowired
    private WebTestClient client;

    public CityResourceIntegrationTest() {
    }

    @Test
    public void shouldCreateCity() {
        CityDTO cityDTO = new CityDTO(707860L);
        client
                .post()
                .uri("/cities")
                .body(Mono.just(cityDTO), CityDTO.class)
                .exchange()
                .expectStatus().isCreated();

    }


    @Test
    public void shouldReturnBadRequestWhenAddingInvalidCityCity() {
        CityDTO cityDTO = new CityDTO(123123L);
        client
                .post()
                .uri("/cities")
                .body(Mono.just(cityDTO), CityDTO.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldDeleteCity() {

        client
                .delete()
                .uri("/cities/{city}", 707860L)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void shouldReturnBadRequestWhenDeletingNotExistingCity() {

        client
                .delete()
                .uri("/cities/{city}", 12312312L)
                .exchange()
                .expectStatus().isBadRequest();
    }

}