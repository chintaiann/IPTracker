package com.example.iptracker_backend.iptracker.repo;

import com.example.iptracker_backend.iptracker.models.IP2Location.IpInfo;
import com.example.iptracker_backend.iptracker.models.LastUpdate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface lastUpdateRepo extends ElasticsearchRepository<LastUpdate, LastUpdate> {

}