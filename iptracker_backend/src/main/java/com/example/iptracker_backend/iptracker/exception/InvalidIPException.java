package com.example.iptracker_backend.iptracker.exception;

import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;

public class InvalidIPException extends MainException {


    public InvalidIPException(String message, HttpServletRequest request, Principal principal, String queryDetails) {
        super(message, request, principal, queryDetails);
    }
}
