package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.exception.CityAlreadyRegisteredException;
import com.baimurzin.myweatherapp.exception.InvalidCityException;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.model.CityRegistry;
import com.baimurzin.myweatherapp.repository.CityRegistryRepository;
import com.baimurzin.myweatherapp.repository.CityRepository;
import com.baimurzin.myweatherapp.service.impl.CityServiceImpl;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.Assert.*;

@Slf4j
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityRegistryRepository cityRegistryRepository;

    @Mock
    private WeatherService weatherService;

    private CityService cityService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        cityService = new CityServiceImpl(cityRepository, cityRegistryRepository, weatherService);
    }

    @Test
    public void shouldThrowInvalidCytException() {
        City city = new City(1123123L, "Moscow");
        CityDTO cityDTO = new CityDTO(city.getCityId());

        Mockito.when(cityRegistryRepository.findById(1123123L))
                .thenReturn(Mono.empty());

        StepVerifier.create(cityService.add(cityDTO))
                .expectError(InvalidCityException.class)
                .verify();
    }

    @Test
    public void shouldThrowCityAlreadyRegisteredException() {
        City city = new City(1123123L, "Moscow");
        CityRegistry cityRegistry = new CityRegistry(
                city.getCityId(), city.getCityName(), "RU", 1.2, 1.3);
        CityDTO cityDTO = new CityDTO(city.getCityId());

        Mockito.when(cityRegistryRepository.findById(1123123L))
                .thenReturn(Mono.just(cityRegistry));

        Mockito.when(cityRepository.existsById(city.getCityId()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(cityService.add(cityDTO))
                .expectError(CityAlreadyRegisteredException.class)
                .verify();
    }

    @Test
    public void shouldSaveCity() {
        City city = new City(1123123L, "Moscow");
        CityRegistry cityRegistry = new CityRegistry(
                city.getCityId(), city.getCityName(), "RU", 1.2, 1.3);
        CityDTO cityDTO = new CityDTO(city.getCityId());

        Mockito.when(cityRegistryRepository.findById(1123123L))
                .thenReturn(Mono.just(cityRegistry));

        Mockito.when(cityRepository.existsById(city.getCityId()))
                .thenReturn(Mono.just(false));

        Mockito.when(cityRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());

        Mockito.when(cityRepository.findById(cityRegistry.getId()))
                .thenReturn(Mono.just(city));

        StepVerifier.create(cityService.add(cityDTO))
                .expectNextMatches(c -> cityDTO.getCityId().equals(c.getCityId()))
                .verifyComplete();
    }


    @Test
    public void shouldThrowInvalidCityException() {
        long id = 1123123L;
        Mockito.when(cityRepository.findById(id))
                .thenReturn(Mono.empty());

        StepVerifier.create(cityService.delete(id))
                .expectError(InvalidCityException.class)
                .verify();

    }

    @Test
    public void shouldDeleteCity() {
        City city = new City(1123123L, "Moscow");

        Mockito.when(cityRepository.findById(city.getCityId()))
                .thenReturn(Mono.just(city));

        Mockito.when(cityRepository.delete(Mockito.any()))
                .thenReturn(Mono.empty());

        Mockito.when(weatherService.deleteWeatherData(city.getCityId()))
                .thenReturn(Mono.empty());

        StepVerifier.create(cityService.delete(city.getCityId()))
                .verifyComplete();

    }
}