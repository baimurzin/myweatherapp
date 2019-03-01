package com.baimurzin.myweatherapp.web.rest;

import com.baimurzin.myweatherapp.exception.AppException;
import com.baimurzin.myweatherapp.exception.CityAlreadyRegisteredException;
import com.baimurzin.myweatherapp.exception.InvalidCityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple exception handler
 *
 * @author Vladislav Baimurzin
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({InvalidCityException.class, CityAlreadyRegisteredException.class})
    public Mono<ResponseEntity> handleInvalidCityException(AppException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("reason", ex.getMessage());
        return Mono.just(ResponseEntity.badRequest().body(map));
    }
}
