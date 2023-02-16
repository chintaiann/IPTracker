package com.iptracker.models;

public class filterList {
	private String country_name; 
	private String isp; 
	private String usage_type;
	
	
	public void printDetails() { 
		System.out.println("Reverse Lookup for: ");
		System.out.println("Country_name: " + this.country_name);
		System.out.println("ISP: "+this.isp);
		System.out.println("Usage Type: "+this.usage_type);
	}
	
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getUsage_type() {
		return usage_type;
	}
	public void setUsage_type(String usage_type) {
		this.usage_type = usage_type;
	} 
	
	
	
	
}
