package com.iptracker.repo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.iptracker.models.IPv4;
import com.iptracker.models.IPv6;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface IPv6Repo extends MongoRepository<IPv6, String> {
	
	//ipv6
    @Query("{'ip_from' : {$lte:?0} , 'ip_to': {$gte:?0}}")
	IPv6 findItemByIP(long ip);    
    
    @Query(" {'country_name' : {$eq:?0} }")
    List<IPv6> findAllByCountry(String country); 
    
    //find all IPv4 with this isp
    @Query(" {'isp' : {$eq:?0} }")
    List<IPv6> findAllByIsp(String isp); 
    
    //find all IPv4 with this usage_type
    @Query(" {'usage_type' : {$eq:?0} }")
    List<IPv6> findAllByUsageType(String usage_type); 
    

    @Aggregation(pipeline = { "{ '$group': { '_id' : '$country_name' } }" })    
    List<String> findDistinctCountries();
  
    @Aggregation(pipeline = { "{ '$group': { '_id' : '$isp' } }" })    
    List<String> findDistinctIsp();
    public long count();
    
}



