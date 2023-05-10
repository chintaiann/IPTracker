package com.example.iptracker_backend.iptracker.logger;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(indexName="activity")
public class loggerModel {
    @Field(name="username")
    private String username;
    @Field(name="logLevel")
    private String logLevel;
    @Field(type = FieldType.Text, name="dateTime")
    private String dateTime;
    @Field(name="message")
    private String message;
    @Field(name="requestType")
    private String requestType;
    @Field(name="url")
    private String url;
    @Field(type = FieldType.Text,name="queryDetails")
    private String queryDetails;

    public loggerModel(String username, String logLevel, String message, String requestType, String url,String queryDetails) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.dateTime = dtf.format(LocalDateTime.now());
        this.username= username;
        this.logLevel = logLevel;
        this.message = message;
        this.requestType = requestType;
        this.url = url;
        this.queryDetails = queryDetails;
    }

    public String getQueryDetails() {
        return queryDetails;
    }

    public void setQueryDetails(String queryDetails) {
        this.queryDetails = queryDetails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
