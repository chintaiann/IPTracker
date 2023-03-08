package com.iptracker.models;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.iptracker.exception.IPNotFoundException;
import com.iptracker.exception.InvalidIPException;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import jakarta.persistence.Id;

@Document("ipv6_million")
public class IPv6 {
	private String address_from;
	private String address_to;
	@Id
	private String ip_from; 
	private String ip_to; 
	private String ip;
	private String country_code;
	private String country_name; 
	private String region_name; 
	private String city_name; 
	private double latitude;
	private double longitude;
	private String zip_code; 
	private String time_zone; 
	private String isp; 
	private String domain; 
	private String net_speed; 
	private String idd_code; 
	private String area_code; 
	private String weather_station_code;
	private String weather_station_name;
	private String mcc; 
	private String mnc; 
	private String mobile_brand; 
	private String elevation;
	private String usage_type; 
	
	@PersistenceConstructor
	public IPv6(String ip_from, String ip_to, String country_code, String country_name, String region_name, String city_name, double latitude, double longitude, String zip_code,String time_zone, 
	String isp, String domain, String net_speed, String idd_code, String area_code, String weather_station_code, String weather_station_name,String mcc, String mnc, String mobile_brand, String elevation, String usage_type) {
		this.setIp_from(ip_from); 
		this.setIp_to(ip_to); 
		this.setCountry_code(country_code);
		this.setCountry_name(country_name); 
		this.setRegion_name(region_name); 
		this.setCity_name(city_name); 
		this.setLatitude(latitude); 
		this.setLongitude(longitude); 
		this.setZip_code(zip_code); 
		this.setTime_zone(time_zone); 
		this.setIsp(isp); 
		this.setDomain(domain); 
		this.setNet_speed(net_speed); 
		this.setIdd_code(idd_code); 
		this.setArea_code(area_code); 
		this.setWeather_station_code(weather_station_code); 
		this.setWeather_station_name(weather_station_name); 
		this.setMcc(mcc); 
		this.setMnc(mnc); 
		this.setMobile_brand(mobile_brand);
		this.setElevation(elevation); 
		this.setUsage_type(usage_type); 
	}
	
	public IPv6(String ip) { 
		this.ip = ip;
	}
	

	@Override 
	public boolean equals(Object o) { 
		if (o == this) { 
			return true;
		}
		
		if (! (o instanceof IPv6)) { 
			return false; 
		}
		
		IPv6 other = (IPv6)o; 
		return ( (this.ip_from.toString().equals(other.ip_from.toString())) && (this.ip_to.toString().equals(other.ip_to.toString())));  
	}
	
	public static IPv6 filterFromList(BigInteger ipNum, List<IPv6> ipList) { //ipNum must be >= ip_from and <= ip_to
		IPv6 result = null;
		for (IPv6 ip : ipList) { 
			BigInteger from = new BigInteger(ip.getIp_from());
			BigInteger to = new BigInteger(ip.getIp_to());
			int compareFrom = ipNum.compareTo(from);
			int compareTo = ipNum.compareTo(to); 
			
			if ((compareFrom == 1 || compareFrom ==0) && (compareTo == -1 || compareTo == 0)) {
				result = ip;
			}
		}

		
		return result; 
	}
	
	
	public static java.math.BigInteger convertToIPNumber(String ipv6) throws UnknownHostException,InvalidIPException {
		try { 
			IPAddressString addrStr = new IPAddressString(ipv6);
			IPAddress addr  = addrStr.getAddress();
			BigInteger value = addr.getValue();
			System.out.println(value);
			return value;
		} catch(Exception e) { 
			throw new InvalidIPException("IPv6 is not of valid format.");
		}
		
	}
	
	
	public static String convertToIPv6 (BigInteger ipnumber)
	{
		String str = ipnumber.toString(16); 
		int len = str.length();
		while(len < 32) {
		    str = "0" + str;
		    len++;
		}
		IPAddressString addrStr = new IPAddressString(str);
		return addrStr.getAddress().toString();
		
	}
	
	public String getIp_from() {
		return ip_from;
	}

	public void setIp_from(String ip_from2) {
		this.ip_from = ip_from2;
	}

	public String getIp_to() {
		return ip_to;
	}

	public void setIp_to(String ip_to2) {
		this.ip_to = ip_to2;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getNet_speed() {
		return net_speed;
	}

	public void setNet_speed(String net_speed) {
		this.net_speed = net_speed;
	}

	public String getIdd_code() {
		return idd_code;
	}

	public void setIdd_code(String idd_code) {
		this.idd_code = idd_code;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getWeather_station_code() {
		return weather_station_code;
	}

	public void setWeather_station_code(String weather_station_code) {
		this.weather_station_code = weather_station_code;
	}

	public String getWeather_station_name() {
		return weather_station_name;
	}

	public void setWeather_station_name(String weather_station_name) {
		this.weather_station_name = weather_station_name;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMobile_brand() {
		return mobile_brand;
	}

	public void setMobile_brand(String mobile_brand) {
		this.mobile_brand = mobile_brand;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getElevation() {
		return elevation;
	}

	public void setElevation(String elevation) {
		this.elevation = elevation;
	}

	public String getUsage_type() {
		return usage_type;
	}

	public void setUsage_type(String usage_type) {
		this.usage_type = usage_type;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAddress_from() { 
		return address_from; 
	}
	
	public void setAddress_from(String addr) { 
		this.address_from = addr;
	}

	public String getAddress_to() { 
		return address_to; 
	}
	
	public void setAddress_to(String addr) { 
		this.address_to = addr;
	}
}
