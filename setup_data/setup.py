
import csv 
import pandas as pd 
import socket 
from elasticsearch import Elasticsearch,helpers
import json
import random
import datetime
from datetime import datetime, timedelta, date
import string
from util import *
from constants import *
import os
print("Setting up indexes.")
ip2location_ipv4 = "ip2location_ipv4"
ip2location_ipv6 = "ip2location_ipv6"

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


es_client = Elasticsearch(
    CONTAINER,
    ca_certs=CERT_LINK,
    basic_auth=(ELASTIC_USERNAME,ELASTIC_PASSWORD))

response = es_client.indices.create(
    index=ip2location_ipv4,
    body=settings,
    ignore=400 # ignore 400 already exists code
)

response = es_client.indices.create(
    index=ip2location_ipv6,
    body=settings,
    ignore=400 # ignore 400 already exists code
)

response = es_client.indices.create( 
    index=GREYNOISE_IPV4,
    ignore=400
)
response = es_client.indices.create( 
    index=GREYNOISE_IPV6,
    ignore=400
)

response = es_client.indices.create( 
    index=TIME_LOG_INDEX,
    ignore=400
)

print("Setup completed.")