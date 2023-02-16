package com.iptracker;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.iptracker.exception.IPNotFoundException;
import com.iptracker.exception.InvalidIPException;
import com.iptracker.models.IPv4;
import com.iptracker.models.IPv6;
import com.iptracker.models.filterList;
import com.iptracker.models.ipList;
import com.iptracker.repo.IPv6Repo;
import com.iptracker.repo.IPv4Repo;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import com.ip2location.*;
import org.springframework.http.HttpStatus;

@RestController

public class AddressController {
	@Autowired
	IPv4Repo IPv4Repo;

	@Autowired
	IPv6Repo IPv6Repo;
	
	//single queries 

	@GetMapping("singleQuery/{protocol}/{ip}")
	public Map<String,Object> singleQuery(@PathVariable String protocol, @PathVariable String ip) throws UnknownHostException, InvalidIPException, IPNotFoundException { 

		Map<String,Object> response = new HashMap<>();
			if (protocol.equals("IPv4")) { 
				long ipNumber = IPv4.convertToIPNumber(ip); 
				IPv4 item = IPv4Repo.findItemByIP(ipNumber);
				if (item != null) { 
					item.setIp(ip);
					response.put("response", item);
				}
				else { 
					throw new IPNotFoundException("Sorry, IP was not found in database.");
				}
				return response; 
				}
			
			
			else { //ipv6
				System.out.println(ip);
				BigInteger ipNumber = IPv6.convertToIPNumber(ip);
				//need to find a way to use BigInteger in MongoDB, this will not work for long. 
				System.out.println("ipNumber:" + ipNumber);
				long numBig = ipNumber.longValue();
				System.out.println("Converted to long:" + numBig);
				IPv6 item = IPv6Repo.findItemByIP(numBig);
				if (item != null) { 
					item.setIp(ip);
					response.put("response", item);
				}
				else { 
					throw new IPNotFoundException("Sorry, IP was not found in database.");
				}
				return response;
			}
		
	
	 
	}
	
	//bulk queries 
	@PostMapping("bulkQuery/{protocol}")
	public Map<String, Object> bulkQuery(@PathVariable String protocol, @ModelAttribute ipList ipList) throws InvalidIPException, UnknownHostException { 
		Map<String,Object> response = new HashMap<>();
		
		if (protocol.equals("IPv4")) { 
			List<IPv4> result = new ArrayList<IPv4>();
			for (String ipaddress : ipList.getIpList()) { 
				long ipNumber = IPv4.convertToIPNumber(ipaddress); 
				IPv4 item = IPv4Repo.findItemByIP(ipNumber); 
				if (item != null) { 
					item.setIp(ipaddress);
					result.add(item);
				}
				else {
					IPv4 item2 = new IPv4(ipaddress);
					result.add(item2);
				}
							}
			response.put("response", result); 
		}
		
		else { 
			List<IPv6> result = new ArrayList<IPv6>();
			for (String ipaddress : ipList.getIpList()) { 
				BigInteger ipNumber = IPv6.convertToIPNumber(ipaddress); 
				long numBig = ipNumber.longValue();
				IPv6 item = IPv6Repo.findItemByIP(numBig); 
				if (item != null) { 
					item.setIp(ipaddress);
					result.add(item);
				}
				
				else { 
					IPv6 item2 = new IPv6(ipaddress); 
					result.add(item2); 
				}
				
			}
			response.put("response", result); 
		}
		return response; 
	}
	
	
//reverse lookup
	@GetMapping("getISP/{protocol}")
	public List<Object> getISP(@PathVariable String protocol) { 
		List<String> isp = new ArrayList<String>();
		if (protocol.equals("IPv4")) { 
			isp = IPv4Repo.findDistinctIsp();
		}
		
		if (protocol.equals("IPv6")) { 
			isp = IPv6Repo.findDistinctIsp();
		}
		Collections.sort(isp);
		
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> nullValue = new HashMap<>();
		nullValue.put("label"," ");
		nullValue.put("value", null);
		result.add(nullValue);
		for (String i : isp) { 
			Map<String,Object> response = new HashMap<>();
			response.put("label", i);
			response.put("value", i);
			result.add(response);
		}
		return result;
	}	
	
//get back range of IPs based on factors provided 
	@PostMapping("reverseLookUp/{protocol}") 
	public Map<String, Object> reverseLookUp(@PathVariable String protocol, @ModelAttribute filterList filterList) throws InvalidIPException { 
		Map<String,Object> response = new HashMap<>();
		List<String> result2 = new ArrayList<String>();
		List<String> result3 = new ArrayList<String>();
		List<String> result4 = new ArrayList<String>();
		filterList.printDetails();
		
		if (protocol.equals("IPv4")) { 
			List<IPv4> result = new ArrayList<IPv4>();
			if (filterList.getCountry_name().equals("null")) { 
				result = IPv4Repo.findAll();
			}
			else { 
				result = IPv4Repo.findAllByCountry(filterList.getCountry_name());
			}
			for (IPv4 ip : result) { 
				result2.add(ip.returnIPRange());
			}
			
			if (filterList.getIsp().equals("null")) { 
				result = IPv4Repo.findAll();
			}else { 
				result = IPv4Repo.findAllByIsp(filterList.getIsp());
			}
			
			for (IPv4 ip : result) { 
				result3.add(ip.returnIPRange());
			}
			if (filterList.getUsage_type().equals("null")) { 
				result = IPv4Repo.findAll();
			}
			else { 
				result = IPv4Repo.findAllByUsageType(filterList.getUsage_type());
			}
			for (IPv4 ip : result) { 
				result4.add(ip.returnIPRange());
			}
		}
		
		else if (protocol.equals("IPv6")) {
			List<IPv6> result = new ArrayList<IPv6>();

			if (filterList.getCountry_name().equals("null")) { 
				result = IPv6Repo.findAll();
			}
			else { 
				result = IPv6Repo.findAllByCountry(filterList.getCountry_name());
			}
			for (IPv6 ip : result) { 
				result2.add(ip.returnIPRange());
			}
			
			
			
			if (filterList.getIsp().equals("null")) { 
				result = IPv6Repo.findAll();
			}else { 
				result = IPv6Repo.findAllByIsp(filterList.getIsp());
			}
			
			for (IPv6 ip : result) { 
				result3.add(ip.returnIPRange());
			}
			if (filterList.getUsage_type().equals("null")) { 
				result = IPv6Repo.findAll();
			}
			else { 
				result = IPv6Repo.findAllByUsageType(filterList.getUsage_type());
			}
			for (IPv6 ip : result) { 
				result4.add(ip.returnIPRange());
			}
		}
 
		result2.retainAll(result3);
		result2.retainAll(result4);
		if (result2.size() == 0 )  {
			throw new InvalidIPException("No available results.");
		}
		response.put("response", result2);
		return response;
	}


	
//	@GetMapping("getIPFromCountry/{protocol}")
//	public Map<String,Object>getIPFromCountry(@PathVariable String protocol) throws IPNotFoundException { 
//		Map<String,Object> response = new HashMap<>();
//		System.out.println(country + usagetype);
//		if (protocol.equals("IPv4")) { 
//			List<IPv4> result = IPv4Repo.findAllByCountry(country);
//			if (result.isEmpty()) {
//				throw new IPNotFoundException("No IPs belong to this country in the database.");
//			}
//			response.put("response", result);
//			}
//		
//		//issue - ip number is abit too big 
//		if (protocol.equals("IPv6")) { 
//			List<IPv6> result = IPv6Repo.findAllByCountry(country);
//			if (result.isEmpty()) {
//				throw new IPNotFoundException("No IPs belong to this country in the database.");
//			}
//			response.put("response", result);
//		}
//		return response;
// 	}
//	
	@GetMapping("convertToIPv6/{ipNumber}")
	public Map<String,Object> convertToIPv6(@PathVariable String ipNumber){
		Map<String,Object> response = new HashMap<>();
		BigInteger i = new BigInteger(ipNumber);
		String s = IPv6.convertToIPv6(i);
		response.put("response",s); 
		return response; 
	}
	
	
	
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
	
//if using bin file 
//commented out as we are using CSV 
//	public String binfile = "/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_backend/src/main/resources/static/IP2LOCATION-LITE-DB1.IPV6.BIN";
//
//	public IPResult searchBin(String ip) { 
//		try { 
//			IP2Location loc = new IP2Location();
//			loc.Open(binfile, true); 
//			
//			IPResult rec = loc.IPQuery(ip); 
//			if ("OK".equals(rec.getStatus()))
//			{
//				System.out.println(rec);
//				loc.Close();
//				return rec;
//			}
//			else
//			{
//				System.out.println("Error." + rec.getStatus());
//				loc.Close();
//				return rec;
//			}
//			
//			
//		} catch(Exception e) { 
//			System.out.println(e); 
//			return null;
//		} 
//	}
//	@GetMapping("singleQuery/{ip}")
//	public IPResult singleQuery(@PathVariable String ip) { 
//		return searchBin(ip);
//	}

//	@GetMapping("bulkQuery/{ip}")
//	public List<IPResult> bulkQuery(@PathVariable List<String> ip) { 
//		List<IPResult> result = new ArrayList<IPResult>();
//		for (String ipaddress : ip) { 			
//			result.add(searchBin(ipaddress));
//		}
//		return result; 
//	}
//	
	
//	
//	@GetMapping("getAllCountries")
//	public List<Object> a() { 
//		List<Object> result = new ArrayList<>();
//		String[] locales = Locale.getISOCountries();
//		
//		for (String countryCode : locales) {
//			Map<String,String> one = new HashMap<>();
//			Locale obj = new Locale("",countryCode); 
//			one.put("value", obj.getDisplayCountry());
//			one.put("label",obj.getDisplayCountry());
//			result.add(one); 
//		}
//		
//		return result;
//
//	}
	

//	@GetMapping("getDistinctCountries/{protocol}")
//	public List<String> getDistinctCountries(@PathVariable String protocol) { 
//		List<String> distinctCountries = new ArrayList<String>();
//		if (protocol.equals("IPv4")) { 
//			distinctCountries = IPv4Repo.findDistinctCountries();
//		}
//		
//		if (protocol.equals("IPv6")) { 
//			distinctCountries = IPv6Repo.findDistinctCountries();
//		}
//		Collections.sort(distinctCountries);
//		return distinctCountries;
//	}
	//reverse 
//	@GetMapping("getIPFromCountry/{protocol}/{country}")
//	public Map<String,Object>getIPFromCountry(@PathVariable String country, @PathVariable String protocol) throws IPNotFoundException { 
//		Map<String,Object> response = new HashMap<>();
//
//		if (protocol.equals("IPv4")) { 
//			List<IPv4> result = IPv4Repo.findAllByCountry(country);
//			if (result.isEmpty()) {
//				throw new IPNotFoundException("No IPs belong to this country in the database.");
//			}
//			response.put("response", result);
//			}
//		
//		//issue - ip number is abit too big 
//		if (protocol.equals("IPv6")) { 
//			List<IPv6> result = IPv6Repo.findAllByCountry(country);
//			if (result.isEmpty()) {
//				throw new IPNotFoundException("No IPs belong to this country in the database.");
//			}
//			response.put("response", result);
//		}
//		return response;
// 	}
	
	
}
