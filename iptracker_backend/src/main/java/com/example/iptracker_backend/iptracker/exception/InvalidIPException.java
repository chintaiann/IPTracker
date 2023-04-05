package com.example.iptracker_backend.iptracker.exception;

public class InvalidIPException extends Exception {

    public InvalidIPException(String message) {
        super(message);
        System.out.println("Error:" + message);

    }
}
