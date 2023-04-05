package com.example.iptracker_backend.iptracker.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.time.Duration;

@Configuration
public class Config extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
//                .connectedTo("es01:9200")
                .connectedTo("localhost:9200")
                .usingSsl()
                // .withBasicAuth("elastic","ajfEu_Bq0tQ+6HYcUJC8")
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(60)) 
                .withBasicAuth("elastic","csit1234")
                .build();
    }

}

