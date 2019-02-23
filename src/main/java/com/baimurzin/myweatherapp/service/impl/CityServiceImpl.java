package com.baimurzin.myweatherapp.service.impl;

import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.repository.CityRepository;
import com.baimurzin.myweatherapp.service.CityService;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Mono<City> add(CityDTO cityDTO) {
        City city = new City();
        city.setCityId(cityDTO.getCityId());
        return cityRepository.save(city);
    }
}
