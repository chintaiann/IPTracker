package com.example.iptracker_backend.iptracker.models.IP2Location;

import com.example.iptracker_backend.iptracker.models.IP2Location.IpInfo;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="ip2l_ipv4")
public class IPv4 extends IpInfo {

//    public static long convertToIPNumber(String IPv4) throws InvalidIPException {
//        try {
//            String[] ipAddressInArray = IPv4.split("\\.");
//            long result = 0;
//            long ip = 0;
//            for (int x = 3; x >= 0; x--) {
//                ip = Long.parseLong(ipAddressInArray[3 - x]);
//                result |= ip << (x << 3);
//            }
//            return result;
//        } catch (Exception e) {
//            throw new InvalidIPException("IPv4 is not of valid format.");
//        }
//    }
//
//    public static String convertNumberToAddress(long ipnum) {
//        String result = "";
//        result = ((ipnum / 16777216) % 256) + "." + ((ipnum / 65536) % 256) + "." + ((ipnum / 256) % 256) + "." + (ipnum % 256);
//        return result;
//    }

}