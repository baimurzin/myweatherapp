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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityRegistryRepository cityRegistryRepository;

    @Override
    public Mono<City> add(CityDTO cityDTO) {
        return checkCity(cityDTO.getCityId())
                .onErrorResume(Mono::error)
                .map(CityRegistry::getId)
                .map(cityRepository::findById)
                .handle((c, sink) -> {
                     if (c.isPresent())
                         sink.error(new CityAlreadyRegisteredException("City with such ID was already registered"));
                     else {
                         CityRegistry cr = cityRegistryRepository.getOne(cityDTO.getCityId());
                         sink.next(cityRepository.save(new City(cr.getId(), cr.getName())));
                     }
                });
    }

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
