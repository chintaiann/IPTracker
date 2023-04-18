package com.example.iptracker_backend.iptracker.exception;

import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;

public class MainException extends Exception{
    private HttpServletRequest request;
    private Principal principal;
    private String queryDetails;

    public MainException(String message, HttpServletRequest request, Principal principal, String queryDetails) {
        super(message);
        this.request = request;
        this.principal = principal;
        this.queryDetails = queryDetails;
        System.out.println("Error:" + message);
    }

    public String getQueryDetails() {
        return queryDetails;
    }

    public void setQueryDetails(String queryDetails) {
        this.queryDetails = queryDetails;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
