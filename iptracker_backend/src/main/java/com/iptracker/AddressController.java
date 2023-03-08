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
import java.util.stream.Collectors;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.common.collect.Sets;
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
				List<IPv6> allIP = IPv6Repo.findAll(); 
		
				BigInteger ipNumber = IPv6.convertToIPNumber(ip);				
				IPv6 result = IPv6.filterFromList(ipNumber,allIP);
				if (result == null) { 
					throw new IPNotFoundException("Sorry IP Was not found in database."); 
				}
				result.setIp(ip);
				response.put("response", result);
				return response;
			}
		
	
	 
	}
	
	//bulk queries 
	@PostMapping("bulkQuery/{protocol}")
	public Map<String, Object> bulkQuery(@PathVariable String protocol, @ModelAttribute ipList ipList) throws InvalidIPException, UnknownHostException { 
		Map<String,Object> response = new HashMap<>();
		
		if (ipList.count() > 50) { 
			throw new InvalidIPException("Size of bulk query is limited to 50.");
		}
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
			List<IPv6> allIP = IPv6Repo.findAll(); 
			List<IPv6> result = new ArrayList<IPv6>();
			
			for (String ipaddress : ipList.getIpList()) { 
				BigInteger ipNumber = IPv6.convertToIPNumber(ipaddress);				
				IPv6 filterFound = IPv6.filterFromList(ipNumber,allIP);
				
				if (filterFound != null) { 
					filterFound.setIp(ipaddress); 
					result.add(filterFound); 
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
	
// 001 010 011 100 101 11o0 111 
	@PostMapping("reverseLookUp/{protocol}") 
	public Map<String, Object> reverseLookUp(@PathVariable String protocol, @ModelAttribute filterList filterList) throws InvalidIPException, IPNotFoundException { 
		Map<String,Object> response = new HashMap<>();
		filterList.printDetails();
		
		try { 
			if (filterList.getCountry_name().equals("") && filterList.getIsp().equals("") && filterList.getUsage_type().equals("")) {
				throw new InvalidIPException("Please choose at least one filter.");
			}
			
			if (protocol.equals("IPv4")) { 
				List<IPv4> countryResult = new ArrayList<IPv4>();
				List<IPv4> ispResult = new ArrayList<IPv4>();
				List<IPv4> usageResult = new ArrayList<IPv4>();
				List<IPv4> finalResult = new ArrayList<IPv4>();
				if (!filterList.getCountry_name().equals("")) { 
					countryResult = IPv4Repo.findAllByCountry(filterList.getCountry_name());
					System.out.println("country size:" + countryResult.size());
				}

				if (!filterList.getIsp().equals("")) { 
					String regex = "^(?i)(.*" + filterList.getIsp() + ".*)$";
					ispResult = IPv4Repo.findAllByIsp(regex);
					System.out.println("isp size:" + ispResult.size());

				}
				
				if (!filterList.getUsage_type().equals("")) { 
					usageResult = IPv4Repo.findAllByUsageType(filterList.getUsage_type());
					System.out.println("usage size:" + usageResult.size());

				}
				
				
				if (countryResult.size()==0 && ispResult.size()==0 && usageResult.size() ==0) { 
					throw new InvalidIPException("No available results.");
				}
				
				if (!(countryResult.size() ==0)) { 
					if (!(ispResult.size()==0) && !(usageResult.size()==0)) { 
						finalResult = findIntersection(countryResult,ispResult); 
						finalResult = findIntersection(finalResult,usageResult);
					}
					if ((ispResult.size()==0) && (usageResult.size()==0)) { 
						finalResult = countryResult;
					}
					if (!(ispResult.size()==0) && (usageResult.size()==0)) { 
						finalResult = findIntersection(countryResult,ispResult); 
					}
					if ((ispResult.size()==0) && !(usageResult.size()==0)) { 
						finalResult = findIntersection(finalResult,usageResult);
					}
				}
				
				else if (!(ispResult.size()==0)) { 
					if (!(usageResult.size()==0)) { 
						finalResult = findIntersection(ispResult,usageResult); 
					}
					else { 
						finalResult = ispResult;
					}
				}

				else {
					finalResult = usageResult;
				}
				
				if (finalResult.size() == 0) { 
					throw new InvalidIPException("No available results.");
				}
				
				for (IPv4 ip : finalResult) { 
					ip.setAddress_from(IPv4.convertNumberToAddress(ip.getIp_from()));
					ip.setAddress_to(IPv4.convertNumberToAddress(ip.getIp_to()));
				}
				response.put("response",finalResult);
				System.out.println("Size of result:" + finalResult.size());
			}
			
			else if (protocol.equals("IPv6")) {
				List<IPv6> countryResult = new ArrayList<IPv6>();
				List<IPv6> ispResult = new ArrayList<IPv6>();
				List<IPv6> usageResult = new ArrayList<IPv6>();
				List<IPv6> finalResult = new ArrayList<IPv6>();
				if (!filterList.getCountry_name().equals("")) { 
					countryResult = IPv6Repo.findAllByCountry(filterList.getCountry_name());
					System.out.println("country size:" + countryResult.size());
				}
	
				if (!filterList.getIsp().equals("")) { 
					String regex = "^(?i)(.*" + filterList.getIsp() + ".*)$";
					ispResult = IPv6Repo.findAllByIsp(regex);
					System.out.println("isp size:" + ispResult.size());
	
				}
				
				if (!filterList.getUsage_type().equals("")) { 
					usageResult = IPv6Repo.findAllByUsageType(filterList.getUsage_type());
					System.out.println("usage size:" + usageResult.size());
	
				}
				
				
				if (countryResult.size()==0 && ispResult.size()==0 && usageResult.size() ==0) { 
					throw new InvalidIPException("No available results.");
				}
				
				if (!(countryResult.size() ==0)) { 
					if (!(ispResult.size()==0) && !(usageResult.size()==0)) { 
						finalResult = findv6Intersection(countryResult,ispResult); 
						finalResult = findv6Intersection(finalResult,usageResult);
					}
					if ((ispResult.size()==0) && (usageResult.size()==0)) { 
						finalResult = countryResult;
					}
					if (!(ispResult.size()==0) && (usageResult.size()==0)) { 
						finalResult = findv6Intersection(countryResult,ispResult); 
					}
					if ((ispResult.size()==0) && !(usageResult.size()==0)) { 
						finalResult = findv6Intersection(finalResult,usageResult);
					}
				}
				
				else if (!(ispResult.size()==0)) { 
					if (!(usageResult.size()==0)) { 
						finalResult = findv6Intersection(ispResult,usageResult); 
					}
					else { 
						finalResult = ispResult;
					}
				}
	
				else {
					finalResult = usageResult;
				}
				
				if (finalResult.size() == 0) { 
					throw new InvalidIPException("No available results.");
				}
				
				for (IPv6 ip : finalResult) { 
					ip.setAddress_from(IPv6.convertToIPv6(new BigInteger(ip.getIp_from())));
					ip.setAddress_to(IPv6.convertToIPv6(new BigInteger(ip.getIp_to())));
					response.put("response",finalResult);
					}
				System.out.println("Size of result:" + finalResult.size());
			}
			
			//check if any result is empty since result of intersection will be 0 
			
		
			return response;
			
			
		} catch (NullPointerException e) { 
			throw new IPNotFoundException("Please ensure all fields are entered. If empty, use " + '"' + '"' +".");
		}

		
		



	}
	
	public List<IPv4> findIntersection(List<IPv4> set1, List<IPv4> set2) { 
		List<IPv4> result = set1.stream().distinct().filter(set2::contains).collect(Collectors.toList());
		return result;
	}
	
	public List<IPv6> findv6Intersection(List<IPv6> set1, List<IPv6> set2) { 
		List<IPv6> result = set1.stream().distinct().filter(set2::contains).collect(Collectors.toList());
		return result;
	}



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
