IP2L_IPV4 ="ip2l_ipv4"
IP2L_IPV6="ip2l_ipv6"
GREYNOISE_IPV4 ="greynoise_ipv4"
GREYNOISE_IPV6="greynoise_ipv6"
TIME_LOG_INDEX="timelog"

#elastic details 
CERT_LINK = "/usr/share/elasticsearch/config/certs/ca/ca.crt"
CONTAINER = "https://es01:9200"
ELASTIC_USERNAME = "elastic"
ELASTIC_PASSWORD = "csit1234"

#raw_data absolute paths 
IP2L_IPV4_DATA = "/setup/IPv4_Elastic.csv"
IP2L_IPV6_DATA = "/setup/IPv6_Elastic.csv"
GREYNOISE_IPV4_DATA = "/setup/Greynoise_IPv4.json"
GREYNOISE_IPV6_DATA = "/setup/Greynoise_IPv6.json"

settings = {  
  "mappings": {
    "properties": {
      "ip_range": {
        "type": "ip_range"
      },
      "country_code": {
        "type": "keyword"
      },
      "country_name": {
        "type": "text"
      },
      "region_name": {
        "type": "text"
      },
      "city_name": {
        "type": "text"
      },
      "latitude": {
        "type": "float"
      },
      "longitude": {
        "type": "float"
      },
      "zip_code": {
        "type": "keyword"
      },
      "time_zone": {
        "type": "keyword"
      },
      "isp": {
        "type": "text"
      },
      "domain": {
        "type": "keyword"
      },
      "net_speed": {
        "type": "keyword"
      },
      "idd_code": {
        "type": "keyword"
      },
      "area_code": {
        "type": "keyword"
      },
      "weather_station_code": {
        "type": "keyword"
      },
      "weather_station_name": {
        "type": "text"
      },
      "mcc": {
        "type": "keyword"
      },
      "mnc": {
        "type": "keyword"
      },
      "mobile_brand": {
        "type": "text"
      },
      "elevation": {
        "type": "integer"
      },
      "usage_type": {
        "type": "text"
      }
    }
  }
}