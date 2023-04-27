package com.example.iptracker_backend.iptracker.models.Greynoise;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="greynoise_ipv6")
public class Greynoise_IPv6 extends GreynoiseInfo{
}
