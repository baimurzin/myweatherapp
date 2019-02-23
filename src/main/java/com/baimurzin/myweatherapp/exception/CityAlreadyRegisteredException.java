package com.baimurzin.myweatherapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CityAlreadyRegisteredException extends RuntimeException{
    public CityAlreadyRegisteredException(String message) {
        super(message);
    }
}
