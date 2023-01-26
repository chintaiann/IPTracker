package com.iptracker.models;

import java.math.BigInteger;

public class Address { 
	private Long ipfrom;
	
	private Long ipto;
	
	private String c; 
	
	private String country;
	
	public Address(Long ipfrom, Long ipto, String c,String country) { 
		this.ipfrom = ipfrom;
		this.ipto = ipto; 
		this.c = c;
		this.country = country; 
	}

	
	public void setfromip(Long ip) {
		this.ipfrom = ip;
	}
	public void settoip(Long ip) {
		this.ipto = ip;
	}
	public void setcountry(String c) {
		this.country = c;
	}
	public Long getfromip() {
		return this.ipfrom;
	}	
	public Long getoip() {
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
	
	public void printDetails() { 
		System.out.println(" Starting IP:" + this.ipfrom + ", \nEnding IP:" + this.ipto + ", \n Country:" + this.country
		);
	}
	
}


