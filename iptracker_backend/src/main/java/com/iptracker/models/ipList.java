package com.iptracker.models;

import java.util.ArrayList;
import java.util.List;

public class ipList {
	private ArrayList<String> ipList;
	
	public ArrayList<String> getIpList() {
		return ipList;
	}

	public void setIpList(ArrayList<String> ipList) {
		this.ipList = ipList;
	} 
	
	public void printDetail() { 
		for (String ipaddress : ipList) { 
			System.out.println(ipaddress);
		}
	}
	@Override
	public String toString() { 
		return this.ipList.toString();
	}
	
	public int count() { 
		return this.ipList.size();
	}
}
