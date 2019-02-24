package com.baimurzin.myweatherapp.service.impl;

import com.baimurzin.myweatherapp.exception.CityAlreadyRegisteredException;
import com.baimurzin.myweatherapp.exception.InvalidCityException;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.model.CityRegistry;
import com.baimurzin.myweatherapp.repository.CityRegistryRepository;
import com.baimurzin.myweatherapp.repository.CityRepository;
import com.baimurzin.myweatherapp.service.CityService;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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

    private final CityRepository cityRepository;

    private final CityRegistryRepository cityRegistryRepository;

    /**
     * Implementation
     *
     * @see CityService
     */
    @Override
    public Mono<City> add(CityDTO cityDTO) {
        log.debug("Registering city...");
        return checkCity(cityDTO.getCityId())
                .map(CityRegistry::getId)
                .map(cityRepository::findById)
                .handle((c, sink) -> {
                     if (c.isPresent())
                         sink.error(new CityAlreadyRegisteredException("City with such ID was already registered"));
                     else {
                         CityRegistry cr = cityRegistryRepository.getOne(cityDTO.getCityId());
                         sink.next(cityRepository.save(new City(cr.getId(), cr.getName())));
                         log.debug("City was registered: {}, {}", cr.getName(), cr.getCountry());
                     }
                });
    }

    @Override
    public Mono<Boolean> exists(Long id) {
        return Mono.just(id)
                .map(cityRepository::existsById);
    }

    @Override
    public Mono<Optional<City>> findById(Long id) {
        return Mono.just(cityRepository.findById(id));
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
                .map(cityRegistryRepository::findById)
                .handle((cr, sink) -> {
                    if (!cr.isPresent())
                        sink.error(new InvalidCityException("No such city in our database. City id: " + id));
                    else
                        sink.next(cr.get());
                });
    }
}
