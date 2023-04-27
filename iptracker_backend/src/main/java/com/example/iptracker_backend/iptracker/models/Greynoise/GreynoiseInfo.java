package com.example.iptracker_backend.iptracker.models.Greynoise;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import org.springframework.data.elasticsearch.annotations.Document;

public class GreynoiseInfo {
    @Field(name="actor")
    private String actor;
    @Field(name="bot",type=FieldType.Boolean)
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
    @Field(name="vpn_service")
    private String vpn_service;
    @Field(name="tags")
    private ArrayList<String> tags;
    @Field(name="seen",type=FieldType.Boolean)
    private boolean seen;
    @Field(name="spoofable",type=FieldType.Boolean)
    private boolean spoofable;
    @Field(name="vpn",type=FieldType.Boolean)
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
    @Field(name="metadata.region")
    private String region;
    @Field(name="raw_data.hassh")
    private ArrayList<ja3_hasshObject> hassh;
    @Field(name="raw_data.ja3")
    private ArrayList<ja3_hasshObject> ja3;
    @Field(name="raw_data.scan")
    private ArrayList<scanObject> scan;
    @Field(name="raw_data.web.paths")
    private ArrayList<String> webpaths;
    @Field(name="raw_data.web.useragents")
    private ArrayList<String> useragents;

    public GreynoiseInfo(ArrayList<ja3_hasshObject> ja3,ArrayList<ja3_hasshObject> hassh,ArrayList<scanObject> scan,ArrayList<String> useragents,ArrayList<String> webpaths,String region,ArrayList<String> tags,String vpn_service,String asn, String category, String city, String country, String organization,String os,String actor,boolean bot,String classification,ArrayList<String>cve,String first_seen,String last_seen,String ip,boolean seen,boolean spoofable,boolean vpn ) {
        this.hassh = hassh;
        this.ja3 = ja3;
        this.scan = scan;
        this.asn = asn;
        this.category = category;
        this.city = city;
        this.country = country;
        this.organization = organization;
        this.os = os;
        this.actor = actor;

        this.classification = classification;
        this.cve = cve;
        this.first_seen = first_seen;
        this.last_seen = last_seen;
        this.ip = ip;


        this.vpn_service = vpn_service;
        this.webpaths = webpaths;
        this.useragents = useragents;
        this.region = region;
        this.tags = tags;


        this.bot = bot ? true : null;
        this.seen = seen ? true : null;
        this.spoofable = (spoofable) ? true : null;
        this.vpn = (vpn) ? true : null;
    }
    public static class ja3_hasshObject {
        private long port;
        private String fingerprint;

        public ja3_hasshObject(long port, String fingerprint) {
            this.port = port;
            this.fingerprint = fingerprint;
        }

        public long getPort() {
            return port;
        }

        public void setPort(long port) {
            this.port = port;
        }

        public String getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(String fingerprint) {
            this.fingerprint = fingerprint;
        }
    }
    public static class scanObject{
        private long port;
        private String protocol;

        public long getPort() {
            return port;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public void setPort(long port) {
            this.port = port;
        }

        public scanObject (long port, String protocol){
            this.port = port;
            this.protocol = protocol;
        }
    }

    public ArrayList<ja3_hasshObject> getJa3() {
        return ja3;
    }

    public void setJa3(ArrayList<ja3_hasshObject> ja3) {
        this.ja3 = ja3;
    }

    public ArrayList<ja3_hasshObject> getHassh() {
        return hassh;
    }

    public void setHassh(ArrayList<ja3_hasshObject> hassh) {
        this.hassh = hassh;
    }

    public ArrayList<String> getUseragents() {
        return useragents;
    }

    public void setUseragents(ArrayList<String> useragents) {
        this.useragents = useragents;
    }

    public ArrayList<String> getWebpaths() {
        return webpaths;
    }

    public void setWebpaths(ArrayList<String> webpaths) {
        this.webpaths = webpaths;
    }

    public ArrayList<scanObject> getScan() {
        return scan;
    }

    public void setScan(ArrayList<scanObject> scan) {
        this.scan = scan;
    }

    public GreynoiseInfo() {

    }


    public String getVpn_service() {
        return vpn_service;
    }

    public void setVpn_service(String vpn_service) {
        this.vpn_service = vpn_service;
    }



    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
