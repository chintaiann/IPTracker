package com.example.iptracker_backend.iptracker.exception;

public class IPNotFoundException extends Exception {

    public IPNotFoundException(String message) {
        super(message);
        System.out.println("Error:" + message);

    }
}
