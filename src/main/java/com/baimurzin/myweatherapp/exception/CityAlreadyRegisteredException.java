package com.baimurzin.myweatherapp.exception;

import lombok.NoArgsConstructor;

/**
 * The exception shows that city was already defined.
 *
 * @author Vladislav Baimurzin
 */
@NoArgsConstructor
public class CityAlreadyRegisteredException extends RuntimeException{
    public CityAlreadyRegisteredException(String message) {
        super(message);
    }
}
