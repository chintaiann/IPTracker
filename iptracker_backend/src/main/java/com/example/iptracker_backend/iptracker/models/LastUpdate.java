package com.example.iptracker_backend.iptracker.models;
import org.springframework.data.annotation.Id;
import co.elastic.clients.util.DateTime;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Document(indexName="timelog")
public class LastUpdate {
    @Id
    @Field(name="document_name")
    private String document;
    @Field(name="updated",type= FieldType.Date)
    private String lastUpdated;

    public LastUpdate(String document, String lastUpdated) throws ParseException {
        this.document = document;
        String inputTimestamp = lastUpdated;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = inputFormat.parse(inputTimestamp);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy @ HH:mm");
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        String outputTimestamp = outputFormat.format(date);
        this.lastUpdated = outputTimestamp;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
