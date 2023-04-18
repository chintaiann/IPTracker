package com.example.iptracker_backend.iptracker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="greynoise-test")
public class GreynoiseInfo {
    @Field(name="actor")
    private String actor;
    @Field(name="bot")
    private boolean bot;
    @Field(name="classification")
    private String classification;
    @Field(name="cve")
    private ArrayList<String> cve;
    @Field(name="first_seen")
    private String first_seen;
    @Field(name="last_seen")
    private String last_seen;
    @Field(name="ip")
    private String ip;
//    @Field(name="metadata")
//    private MetaData metadata;
    @Field(name="seen")
    private boolean seen;
    @Field(name="spoofable")
    private boolean spoofable;
    @Field(name="vpn")
    private boolean vpn;
    @Field(name="metadata.os")
    private String os;
    @Field(name="metadata.asn")
    private String asn;
    @Field(name="metadata.category")
    private String category;
    @Field(name="metadata.city")
    private String city;
    @Field(name="metadata.country")
    private String country;
    @Field(name="metadata.organization")
    private String organization;
    public GreynoiseInfo() {

    }


    public GreynoiseInfo(String asn, String category, String city, String country, String organization,String os,String actor,boolean bot,String classification,ArrayList<String>cve,String first_seen,String last_seen,String ip,boolean seen,boolean spoofable,boolean vpn ) {
        this.asn = asn;
        this.category=category;
        this.city=city;
        this.country=country;
        this.organization = organization;
        this.os=os;
        this.actor=actor;
        this.bot = bot;
        this.classification=classification;
        this.cve = cve;
        this.first_seen = first_seen;
        this.last_seen = last_seen;
        this.ip = ip;
        this.spoofable = spoofable;
        this.seen = seen;
        this.vpn = vpn;

    }


    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
    //    public GreynoiseInfo.MetaData getMetaData() {
//        return metadata;
//    }
//
//    public void setMetaData(GreynoiseInfo.MetaData metaData) {
//        this.metadata = metaData;
//    }



    public static class MetaData {
        private String asn;
        private String category;
        private String city;
        private String country;
        private String country_code;
        private String organization;
        private String os;
        private String rdns;
        private String region;
        private boolean tor;

        public MetaData(String asn, String category, String city, String country, String country_code, String org, String os, String rdns, String region, boolean tor) {
            this.setAsn(asn);
            this.setCategory(category);
            this.setCity(city);
            this.setCountry(country);
            this.setCountry_code(country_code);
            this.setOrganization(org);
            this.setOs(os);
            this.setRdns(rdns);
            this.setRegion(region);
            this.setTor(tor);
        }

        public boolean getTor() {
            return tor;
        }

        public void setTor(boolean tor) {
            this.tor = tor;
        }

        public String getRdns() {
            return rdns;
        }

        public void setRdns(String rdns) {
            this.rdns = rdns;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }


        public String getAsn() {
            return asn;
        }

        public void setAsn(String asn) {
            this.asn = asn;
        }
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public ArrayList<String> getCve() {
        return cve;
    }

    public void setCve(ArrayList<String> cve) {
        this.cve = cve;
    }

    public String getFirst_seen() {
        return first_seen;
    }

    public void setFirst_seen(String first_seen) {
        this.first_seen = first_seen;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isVpn() {
        return vpn;
    }

    public void setVpn(boolean vpn) {
        this.vpn = vpn;
    }

    public boolean isSpoofable() {
        return spoofable;
    }

    public void setSpoofable(boolean spoofable) {
        this.spoofable = spoofable;
    }


}
