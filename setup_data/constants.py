ip2location_ipv4_index = "ipv4"
ip2location_ipv6_index = "ipv6"
greynoise_ipv4_index = "greynoise_ipv4"
time_log_index = "timelog"


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