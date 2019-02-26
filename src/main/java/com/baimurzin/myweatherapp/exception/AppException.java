package com.baimurzin.myweatherapp.exception;

/**
 * The root App exception.
 *
 * @author Vladislav Baimurzin
 */
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

}
