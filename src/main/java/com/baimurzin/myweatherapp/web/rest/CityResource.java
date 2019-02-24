package com.baimurzin.myweatherapp.web.rest;

import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.service.CityService;
import com.baimurzin.myweatherapp.web.rest.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST Controller for view and managing {@link City} resource
 *
 * @author Vladislav Baimurzin
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class CityResource {

    private final CityService cityService;

    /**
     *
     * @param cityDTO
     * @return
     */
    @PostMapping("/cities")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<City> add(@RequestBody CityDTO cityDTO) {
        return cityService.add(cityDTO);
    }


    @DeleteMapping("/cities/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Object> delete(@PathVariable Long cityId) {
        return cityService.delete(cityId);
    }
}
