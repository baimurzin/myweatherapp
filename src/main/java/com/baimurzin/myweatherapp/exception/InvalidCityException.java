package com.baimurzin.myweatherapp.exception;

import lombok.NoArgsConstructor;

/**
 * The exception indicates that city input was incorrect
 * and such does not exists in our known sources
 *
 * @author Vladislav Baimurzin
 */
@NoArgsConstructor
public class InvalidCityException extends RuntimeException {
    public InvalidCityException(String s) {
    }
}
