package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.exception.CityAlreadyRegisteredException;
import com.baimurzin.myweatherapp.exception.InvalidCityException;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service provides methods and abstraction to manage
 * city resources. That can produces {@link Mono} or {@link reactor.core.publisher.Flux}
 * as a result of method execution
 *
 * @author Vladislav Baimurzin
 */
public interface CityService {

    /**
     * Method allows to register new city in application.
     * That will be tracked for weather data processing.
     *
     * @param cityDTO Representation of city
     * @return Mono of {@link City} that was created
     * @throws CityAlreadyRegisteredException in case of duplicate city found
     * @throws InvalidCityException in case of invalid parameters passed as an input
     */
    Mono<City> add(CityDTO cityDTO) throws CityAlreadyRegisteredException, InvalidCityException;

    /**
     * Method retrieves a {@link City} by its id.
     *
     * @param id must not be null.
     * @return the {@link Mono} of {@link City} entity
     */
    Mono<City> findById(Long id);

    /**
     * Method retrieve all objects
     *
     * @return all entities as a Flux
     */
    Flux<City> findAll();

    /**
     * Delete certain entity if it exists.
     * Method also delete other data related to {@link City}
     *
     * @param cityId
     * @return
     */
    Mono<Object> delete(Long cityId);
}
