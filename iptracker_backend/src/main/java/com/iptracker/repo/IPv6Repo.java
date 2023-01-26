package com.iptracker.repo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.iptracker.models.Address;
import com.iptracker.models.IPv4;
import com.iptracker.models.IPv6;

import java.math.BigInteger;
import java.util.List;

public interface IPv6Repo extends MongoRepository<IPv6, String> {
	
	//ipv6
    @Query("{'ipfrom' : {$lte:?0} , 'ipto': {$gte:?0}}")
	IPv6 findItemByStartIP(BigInteger ipfrom);    
    
    @Query(" {'country' : {$eq:?0} }")
    List<IPv6> findAllByCountry(String country); 
    
    @Aggregation(pipeline = { "{ '$group': { '_id' : '$country' } }" })    
    List<String> findDistinctCountries();
    
    
    
    public long count();
    
}



