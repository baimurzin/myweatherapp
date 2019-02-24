package com.baimurzin.myweatherapp.exception;

/**
 * The exception indicates that city input was incorrect
 * and such does not exists in our known sources
 *
 * @author Vladislav Baimurzin
 */
public class InvalidCityException extends AppException {
    public InvalidCityException() {
        super();
    }

    public InvalidCityException(String message) {
        super(message);
    }

    public InvalidCityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCityException(Throwable cause) {
        super(cause);
    }

    protected InvalidCityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
