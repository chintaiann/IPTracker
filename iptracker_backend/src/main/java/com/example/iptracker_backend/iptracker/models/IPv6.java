package com.example.iptracker_backend.iptracker.models;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="ipv6")

public class IPv6 extends IpInfo{
}
