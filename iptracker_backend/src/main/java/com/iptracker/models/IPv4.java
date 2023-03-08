package com.iptracker.models;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.iptracker.exception.InvalidIPException;

import jakarta.persistence.Id;

//@Document("ipv4_full")
@Document("ipv4_million")
public class IPv4 {
	private String ip; 
	private String address_from;
	private String address_to;
	
	@Id
	private long ip_from; 
	private long ip_to; 
	private String country_code;
	private String country_name; 
	private String region_name; 
	private String city_name; 
	private double latitude;
	private double longitude;
	private int zip_code; 
	private String time_zone; 
	private String isp; 
	private String domain; 
	private String net_speed; 
	private int idd_code; 
	private String area_code; 
	private String weather_station_code;
	private String weather_station_name;
	private String mcc; 
	private String mnc; 
	private String mobile_brand; 
	private int elevation;
	private String usage_type; 

	@PersistenceConstructor
	public IPv4(long ip_from, long ip_to, String country_code, String country_name, String region_name, String city_name, double latitude, double longitude, int zip_code,String time_zone, 
	String isp, String domain, String net_speed, int idd_code, String area_code, String weather_station_code, String weather_station_name,String mcc, String mnc, String mobile_brand, int elevation, String usage_type) {
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
	
	
	public IPv4(String ip) {
		this.ip = ip;
	}
	
	public static long convertToIPNumber(String IPv4) throws InvalidIPException  { 
		try { 
			String[] ipAddressInArray = IPv4.split("\\.");
			long result = 0;
			long ip = 0;
			for (int x = 3; x >= 0; x--) {
				ip = Long.parseLong(ipAddressInArray[3 - x]);
				result |= ip << (x << 3);
			}
			return result;
		} catch (Exception e) { 
			throw new InvalidIPException("IPv4 is not of valid format."); 
		}
			
	}

	public static String convertNumberToAddress(long ipnum) {
		String result = "";
		result = ((ipnum / 16777216) % 256) + "." + ((ipnum / 65536) % 256) + "." + ((ipnum / 256) % 256) + "." + (ipnum % 256);
		return result;
	}
	
	@Override 
	public boolean equals(Object o) { 
		if (o == this) { 
			return true;
		}
		
		if (! (o instanceof IPv4)) { 
			return false; 
		}
		
		IPv4 other = (IPv4)o; 
		return ( (this.ip_from == other.ip_from) && (this.ip_to == other.ip_to));  
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
	public long getIp_from() {
		return ip_from;
	}

	public void setIp_from(long ip_from) {
		this.ip_from = ip_from;
	}

	public long getIp_to() {
		return ip_to;
	}

	public void setIp_to(long ip_to) {
		this.ip_to = ip_to;
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

	public int getZip_code() {
		return zip_code;
	}

	public void setZip_code(int zip_code) {
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

	public int getIdd_code() {
		return idd_code;
	}

	public void setIdd_code(int idd_code) {
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

	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
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
}


