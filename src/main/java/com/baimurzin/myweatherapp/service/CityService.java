package com.baimurzin.myweatherapp.service;

import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import reactor.core.publisher.Mono;

public interface CityService {

    Mono<City> add(CityDTO cityDTO);
}
