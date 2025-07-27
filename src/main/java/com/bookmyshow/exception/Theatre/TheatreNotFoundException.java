package com.bookmyshow.exception.Theatre;

public class TheatreNotFoundException extends RuntimeException {
    public TheatreNotFoundException(String id) {
        super("Theatre not found with ID: " + id);
    }
}
