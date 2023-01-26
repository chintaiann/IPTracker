package com.iptracker.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.iptracker.models.Address;
import com.iptracker.models.IPv4;


public interface IPv4Repo extends MongoRepository<IPv4, String> {
	
    //ipv4
    @Query("{'ipfrom' : {$lte:?0} , 'ipto': {$gte:?0}}")
	IPv4 findItemByIP(long ipfrom);    

  
    
    @Query(" {'country' : {$eq:?0} }")
    List<IPv4> findAllByCountry(String country); 
    
    @Aggregation(pipeline = { "{ '$group': { '_id' : '$country' } }" })    
    List<String> findDistinctCountries();
    
 
    public long count();
    
}

