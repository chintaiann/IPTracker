package com.example.iptracker_backend.iptracker.models.IP2Location;

import com.example.iptracker_backend.iptracker.models.IP2Location.IpInfo;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="ip2l_ipv6")

public class IPv6 extends IpInfo {
}
