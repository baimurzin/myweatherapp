package com.baimurzin.myweatherapp.exception;

/**
 * The exception shows that city was already defined.
 *
 * @author Vladislav Baimurzin
 */
public class CityAlreadyRegisteredException extends AppException{

    public CityAlreadyRegisteredException() {
        super();
    }

    public CityAlreadyRegisteredException(String message) {
        super(message);
    }

    public CityAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }

    protected CityAlreadyRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
