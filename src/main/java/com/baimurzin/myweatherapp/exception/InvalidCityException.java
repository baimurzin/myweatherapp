package com.baimurzin.myweatherapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCityException extends RuntimeException {
    public InvalidCityException(String s) {
    }
}
