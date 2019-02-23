package com.baimurzin.myweatherapp.web.rest;

import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.service.CityService;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CityResource {

    private final CityService cityService;

    @PostMapping("/cities")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<City> add(@RequestBody CityDTO cityDTO) {
        return cityService.add(cityDTO);
    }
}
