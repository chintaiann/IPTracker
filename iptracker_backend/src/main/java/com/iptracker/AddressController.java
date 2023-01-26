package com.iptracker;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.iptracker.models.IPv4;
import com.iptracker.models.IPv6;
import com.iptracker.repo.IPv6Repo;
import com.iptracker.repo.IPv4Repo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;


import com.ip2location.*;

@RestController
public class AddressController {
	
	public String binfile = "/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_backend/src/main/resources/static/IP2LOCATION-LITE-DB1.IPV6.BIN";

	public IPResult searchBin(String ip) { 
		try { 
			IP2Location loc = new IP2Location();
			loc.Open(binfile, true); 
			
			IPResult rec = loc.IPQuery(ip); 
			if ("OK".equals(rec.getStatus()))
			{
				System.out.println(rec);
				loc.Close();
				return rec;
			}
			else
			{
				System.out.println("Error." + rec.getStatus());
				loc.Close();
				return rec;
			}
			
			
		} catch(Exception e) { 
			System.out.println(e); 
			return null;
		} 
	}
	
	@Autowired
	IPv4Repo IPv4Repo;

	@Autowired
	IPv6Repo IPv6Repo;
	
	
	
	@GetMapping("singleQuery/{ip}")
	public IPResult singleQuery(@PathVariable String ip) { 
		return searchBin(ip);
	}

	
	
	@GetMapping("bulkQuery/{ip}")
	public List<IPResult> bulkQuery(@PathVariable List<String> ip) { 
		List<IPResult> result = new ArrayList<IPResult>();
		for (String ipaddress : ip) { 			
			result.add(searchBin(ipaddress));
		}
		return result; 
	}
	
	@GetMapping("getDistinctCountries/{protocol}")
	public List<String> getDistinctCountries(@PathVariable String protocol) { 
		List<String> distinctCountries = new ArrayList<String>();
		if (protocol.equals("IPv4")) { 
			distinctCountries = IPv4Repo.findDistinctCountries();
		}
		
		if (protocol.equals("IPv6")) { 
			distinctCountries = IPv6Repo.findDistinctCountries();
		}
		Collections.sort(distinctCountries);
		return distinctCountries;
	}
	
	@GetMapping("getIPFromCountry/{protocol}/{country}")
	public List<String> getIPFromCountry(@PathVariable String country, @PathVariable String protocol) { 
		List<String> resultString = new ArrayList<String>();
		
		if (protocol.equals("IPv4")) { 
			List<IPv4> result = IPv4Repo.findAllByCountry(country);
			resultString = new ArrayList<String>(); 
			for (IPv4 ip : result) { 
				resultString.add(ip.getfromip() + " to " + ip.getoip());
			}
		}
		
		//issue - ip number is abit too big 
		if (protocol.equals("IPv6")) { 
			List<IPv6> result = IPv6Repo.findAllByCountry(country);
			resultString = new ArrayList<String>(); 
			for (IPv6 ip : result) { 
				resultString.add(ip.returnIP());
			}
		}
		return resultString;
 	}
	
	
	
	

//	@GetMapping("singleQueryIPv6/{ip}")
//	public Map<String, Object> singleQuery(@PathVariable String ip) { 
//			BigInteger ipNumber = IPv6.IPv6ToIP(ip);
//			System.out.println(ipNumber);
//			IPv6 item = IPv6Repo.findItemByStartIP(ipNumber);
//
//			if (item != null) { 
//				item.printDetails();
//				Map<String,Object> map = new HashMap<>();
//				map.put("startIP", item.getfromip()); 
//				map.put("endIP", item.getoip()); 
//				map.put("country", item.getcountry());
//				
//				return map;
//			}
//			else {
//				System.out.println("Address not found");
//				return null;
//			}
//		}
//	
//	//ipv4
//	@GetMapping("singleQueryIPv4/{ip}")
//	public Map<String, Object> singleQueryIPv4(@PathVariable String ip) { 
//			long ipNumber = IPv4.convertToIPNumber(ip);
//			System.out.println(ipNumber);
//			IPv4 item = IPv4Repo.findItemByIP(ipNumber);
//
//			if (item != null) { 
//				item.printDetails();
//				Map<String,Object> map = new HashMap<>();
//				map.put("startIP", item.getfromip()); 
//				map.put("endIP", item.getoip()); 
//				map.put("country", item.getcountry());
//				
//				return map;
//			}
//			else {
//				System.out.println("Address not found");
//				return null;
//			}
//		}
	

	

	
	
	@GetMapping("greynoise/{ip}")
	public String fetch(@PathVariable String ip) { 
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.greynoise.io/v3/community/"+ip))
				.header("accept", "application/json")
				.header("key", "8kLtrgyBpD9e9pkzt3OQk9hYXImZWyUoOruSjZNI9XDsNHItz4fs8Pz5Y8Zh6TmU")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();

		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(response.body());
		
		return response.body();
	}
	

	
	
}
