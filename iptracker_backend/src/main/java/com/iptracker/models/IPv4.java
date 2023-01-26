package com.iptracker.models;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ipv4")
public class IPv4 extends Address{

	public IPv4(Long ipfrom, Long ipto, String c, String country) {
		super(ipfrom, ipto, c, country);
		// TODO Auto-generated constructor stub
	}
	
	public static long convertToIPNumber(String IPv4) { 
			String[] ipAddressInArray = IPv4.split("\\.");
			long result = 0;
			long ip = 0;
			for (int x = 3; x >= 0; x--) {
				ip = Long.parseLong(ipAddressInArray[3 - x]);
				result |= ip << (x << 3);
			}
			return result;
		}
	
	
}
