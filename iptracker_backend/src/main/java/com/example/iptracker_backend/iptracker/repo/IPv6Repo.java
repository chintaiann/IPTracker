package com.example.iptracker_backend.iptracker.repo;


import com.example.iptracker_backend.iptracker.models.IP2Location.IPv6;
import com.example.iptracker_backend.iptracker.models.IP2Location.IpInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPv6Repo extends ElasticsearchRepository<IPv6, IpInfo.IpRange> {

}