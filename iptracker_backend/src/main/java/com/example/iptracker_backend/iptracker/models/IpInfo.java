package com.example.iptracker_backend.iptracker.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class IpInfo {
    @Id
    @Field(type = FieldType.Ip_Range, name="ip_range")
    private IpRange ipRange;
    @Field(name="country_code")
    private String countryCode;
    @Field(name="country_name")
    private String countryName;
    @Field(name="region_name")
    private String regionName;
    @Field(name="city_name")
    private String cityName;
    private double latitude;
    private double longitude;
    @Field(name="zip_code")
    private String zipCode;
    @Field(name="time_zone")
    private String timeZone;
    private String isp;
    private String domain;
    @Field(name="net_speed")
    private String netSpeed;
    @Field(name="idd_code")
    private String iddCode;
    @Field(name="area_code")
    private String areaCode;
    @Field(name="weather_station_code")
    private String weatherStationCode;
    @Field(name="weather_station_name")
    private String weatherStationName;
    private String mcc;
    private String mnc;
    @Field(name="mobile_brand")
    private String mobileBrand;
    private int elevation;
    @Field(name="usage_type")
    private String usageType;
    private String enteredip;


    public String getEnteredip() {
        return enteredip;
    }

    public void setEnteredip(String enteredip) {
        this.enteredip = enteredip;
    }

    public IpInfo() {
    }
    public IpInfo(String enteredip) {
        this.setEnteredip(enteredip);
    }

    public IpInfo(IpRange ipRange,String country_code, String country_name, String region_name, String city_name, double latitude, double longitude, String zip_code, String time_zone,
                  String isp, String domain, String net_speed, String idd_code, String area_code, String weather_station_code, String weather_station_name, String mcc, String mnc, String mobile_brand, int elevation, String usage_type) {

        this.setIpRange(ipRange);
        this.setCountryCode(country_code);
        this.setCountryName(country_name);
        this.setRegionName(region_name);
        this.setCityName(city_name);
        this.setLatitude(Math.round(latitude*100.0)/100.0);
        this.setLongitude(Math.round(longitude*100.0)/100.0);
        this.setZipCode(zip_code);
        this.setTimeZone(time_zone);
        this.setIsp(isp);
        this.setDomain(domain);
        this.setNetSpeed(net_speed);
        this.setIddCode(idd_code);
        this.setAreaCode(area_code);
        this.setWeatherStationCode(weather_station_code);
        this.setWeatherStationName(weather_station_name);
        this.setMcc(mcc);
        this.setMnc(mnc);
        this.setMobileBrand(mobile_brand);
        this.setElevation(elevation);
        this.setUsageType(usage_type);
    }




    public static class IpRange {
        private String gte;
        private String lte;
        // getters and setters
        public void setGte(String gte) {
            this.gte = gte;
        }

        public void setLte(String lte) {
            this.lte = lte;
        }

        public IpRange(String gte,String lte) {
            this.setGte(gte);
            this.setLte(lte);
        }

        public String getGte() {
            return gte;
        }

        public String getLte() {
            return lte;
        }
    }

    // getters and setter

    public IpRange getIpRange() {
        return ipRange;
    }

    public void setIpRange(IpRange ipRange) {
        this.ipRange = ipRange;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWeatherStationName() {
        return weatherStationName;
    }

    public void setWeatherStationName(String weatherStationName) {
        this.weatherStationName = weatherStationName;
    }

    public String getWeatherStationCode() {
        return weatherStationCode;
    }

    public void setWeatherStationCode(String weatherStationCode) {
        this.weatherStationCode = weatherStationCode;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getNetSpeed() {
        return netSpeed;
    }

    public void setNetSpeed(String netSpeed) {
        this.netSpeed = netSpeed;
    }

    public String getMobileBrand() {
        return mobileBrand;
    }

    public void setMobileBrand(String mobileBrand) {
        this.mobileBrand = mobileBrand;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getIddCode() {
        return iddCode;
    }

    public void setIddCode(String iddCode) {
        this.iddCode = iddCode;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
