package com.iptracker.models;
import java.math.BigInteger;
import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("j")
public class IPv6{

	private Double ipfrom;
	
	private Double ipto;
	
	private String c; 
	
	private String country;
	
	public IPv6(Double ipfrom, Double ipto, String c, String country) {
		this.ipfrom = ipfrom; 
		this.ipto = ipto; 
		this.c = c;
		this.country = country; 
	}
	
	public void setfromip(Double ip) {
		this.ipfrom = ip;
	}
	public void settoip(Double ip) {
		this.ipto = ip;
	}
	public void setcountry(String c) {
		this.country = c;
	}
	public Double getfromip() {
		return this.ipfrom;
	}	
	public Double getoip() {
		return this.ipto;
	}	
	public String getcountry() {
		return this.country;
	}
	
	public void setc(String c) {
		this.c = c; 
	}
	public String getc() { 
		return this.c; 
	}
	
	public String returnIP() { 
		String from = String.format("%.0f", Double.parseDouble(this.ipfrom.toString()));
		String to = String.format("%.0f", Double.parseDouble(this.ipto.toString()));
		
		return from + " to " + to;

	}
	
	public void printDetails() { 
		System.out.println(" Starting IP: " + this.ipfrom + ", Ending IP:" + this.ipto + ", Country:" + this.country + "\n");
	}
	

	

	
	public static java.math.BigInteger IPv6ToIP(String ipv6) {
		java.net.InetAddress ia;
		try {
			ia = java.net.InetAddress.getByName(ipv6);
			byte byteArr[] = ia.getAddress();
			if (ia instanceof java.net.Inet6Address) {
				java.math.BigInteger ipnumber = new java.math.BigInteger(1, byteArr);
				return ipnumber;
			}
			else {
				return null;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
