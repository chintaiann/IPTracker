package com.example.iptracker_backend.iptracker.advice;

import java.util.HashMap;
import java.util.Map;

import com.example.iptracker_backend.iptracker.controller.IPController;
import com.example.iptracker_backend.iptracker.exception.MainException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.example.iptracker_backend.iptracker.exception.IPNotFoundException;
import com.example.iptracker_backend.iptracker.exception.InvalidIPException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    private ElasticsearchOperations elasticsearchOperations;
    private boolean log = true;
    public ApplicationExceptionHandler(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void logError(MainException ex){
        if (log) {
            IPController IPController = new IPController(elasticsearchOperations);
            IPController.indexLog(ex.getRequest(),ex.getPrincipal(),"ERROR",ex.getMessage(), ex.getQueryDetails());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidIPException.class)
    public Map<String, String> handleInvalidIP(InvalidIPException ex) {
        logError(ex);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IPNotFoundException.class)
    public Map<String, String> handleIPNotFound(IPNotFoundException ex) {
        logError(ex);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }





}
