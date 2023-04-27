package com.example.iptracker_backend.iptracker.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.time.Duration;

@Configuration
public class Config extends ElasticsearchConfiguration {
    @Value("${elasticsearch.address}")
    private String address;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;


    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(address)
                .usingSsl()
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(60)) 
                .withBasicAuth(username,password)
                .build();
    }

}

