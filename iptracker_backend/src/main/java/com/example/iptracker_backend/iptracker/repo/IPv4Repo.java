package com.example.iptracker_backend.iptracker.repo;

import com.example.iptracker_backend.iptracker.models.IPv4;
import com.example.iptracker_backend.iptracker.models.IpInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPv4Repo extends ElasticsearchRepository<IPv4, IpInfo.IpRange> {

}
