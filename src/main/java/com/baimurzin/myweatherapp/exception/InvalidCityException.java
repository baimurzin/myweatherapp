package com.baimurzin.myweatherapp.exception;

/**
 * The exception indicates that city input was incorrect
 * and such does not exists in our known sources
 *
 * @author Vladislav Baimurzin
 */
public class InvalidCityException extends AppException {

    public InvalidCityException(String message) {
        super(message);
    }

}
