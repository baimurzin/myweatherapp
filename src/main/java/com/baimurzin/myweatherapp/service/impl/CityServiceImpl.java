package com.baimurzin.myweatherapp.service.impl;

import com.baimurzin.myweatherapp.exception.CityAlreadyRegisteredException;
import com.baimurzin.myweatherapp.exception.InvalidCityException;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.model.CityRegistry;
import com.baimurzin.myweatherapp.repository.CityRegistryRepository;
import com.baimurzin.myweatherapp.repository.CityRepository;
import com.baimurzin.myweatherapp.service.CityService;
import com.baimurzin.myweatherapp.service.WeatherService;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Basic implementation of {@link CityService}
 *
 * @author Vladislav Baimurzin
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired
    private final CityRepository cityRepository;

    @Autowired
    private final CityRegistryRepository cityRegistryRepository;

    @Autowired
    private final WeatherService weatherService;

    /**
     * Implementation
     *
     * @see CityService
     */
    @Override
    public Mono<City> add(CityDTO cityDTO) {
        log.debug("Registering city...");
        return Mono.just(cityDTO.getCityId())
                .flatMap(this::checkCity)
                .flatMap(cr -> cityRepository.existsById(cr.getId()))
                .flatMap(exists -> exists
                        ? Mono.error(new CityAlreadyRegisteredException("City with such ID was already registered"))
                        : cityRegistryRepository.findById(cityDTO.getCityId())
                        .flatMap(cr -> cityRepository.save(new City(cr.getId(), cr.getName()))
                                .switchIfEmpty(cityRepository.findById(cr.getId()))));

    }

    @Override
    public Mono<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public Flux<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Mono<Object> delete(Long cityId) {
        return cityRepository.findById(cityId)
                .switchIfEmpty(Mono.error(() -> new InvalidCityException("City not found.")))
                .flatMap(city -> cityRepository.delete(city)
                        .then(weatherService.deleteWeatherData(cityId)));
    }

    /**
     * Check if city with id exists in known sources
     * otherwise raise an Exception
     *
     * @param id city id
     * @return Mono of found {@link CityRegistry}
     */
    private Mono<CityRegistry> checkCity(Long id) {
        return Mono.just(id)
                .flatMap(cityRegistryRepository::findById)
                .switchIfEmpty(Mono.error(() -> new InvalidCityException("No such city in our registry database. City id: " + id)));
    }
}
