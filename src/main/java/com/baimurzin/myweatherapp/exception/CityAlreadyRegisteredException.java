package com.baimurzin.myweatherapp.exception;

/**
 * The exception shows that city was already defined.
 *
 * @author Vladislav Baimurzin
 */
public class CityAlreadyRegisteredException extends AppException{

    public CityAlreadyRegisteredException(String message) {
        super(message);
    }

}
