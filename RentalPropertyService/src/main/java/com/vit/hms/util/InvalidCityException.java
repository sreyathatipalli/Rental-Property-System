package com.vit.hms.util;

public class InvalidCityException extends Exception {
    public InvalidCityException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}