package com.example.iptracker_backend.iptracker.models.Greynoise;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="greynoise_ipv4")
public class Greynoise_IPv4 extends GreynoiseInfo{
}
