package com.iptracker.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.iptracker.models.IPv4;


public interface IPv4Repo extends MongoRepository<IPv4, Long> {
	
    //
    @Query("{'ip_from' : {$lte:?0} , 'ip_to': {$gte:?0}}")
	IPv4 findItemByIP(long ip);    
    
    //find all IPv4 with this country_name 
    @Query(" {'country_name' : {$eq:?0} }")
    List<IPv4> findAllByCountry(String country_name); 
    
    //find all IPv4 whose ISP contains the string isp 
    @Query(" {'isp' : {$regex: ?0} }")
    List<IPv4> findAllByIsp(String isp); 
    
    //find all IPv4 with this usage_type
    @Query(" {'usage_type' : {$eq:?0} }")
    List<IPv4> findAllByUsageType(String usage_type); 
    
    
    //obtain list of all countries 
    @Aggregation(pipeline = { "{ '$group': { '_id' : '$country_name' } }" })    
    List<String> findDistinctCountries();
    
    @Aggregation(pipeline = { "{ '$group': { '_id' : '$isp' } }" })    
    List<String> findDistinctIsp();
    public long count();
    
    
    
}

