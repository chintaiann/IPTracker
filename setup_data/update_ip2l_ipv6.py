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

IP2L_IPV6="ip2location_ipv6"

#configuration for ES indexing 
es_client = Elasticsearch(
    CONTAINER,
    ca_certs=CERT_LINK,
    basic_auth=(ELASTIC_USERNAME,ELASTIC_PASSWORD))


response = es_client.indices.create(
    index=IP2L_IPV6,
    body=settings,
    ignore=400 # ignore 400 already exists code
)

response = es_client.indices.create( 
    index=TIME_LOG_INDEX,
    ignore=400
)

def import_ip2l_ipv6():
    print("Indexing IPv6 documents now")
    with open("IPv6_Elastic_Updated.csv", "r") as fi:
        reader = csv.DictReader(fi, delimiter=",")
        id = 1 
        for row in reader:
            doc = {
                "_index": IP2L_IPV6,
                "_id": id,
                "_source": {
                    "ip_range": { 
                        "gte": row["ip_from"] ,
                        "lte": row["ip_to"],
                    },
                    "country_code": row["country_code"],
                      "country_name": row["country_name"],
                      "region_name": row["region_name"],
                      "city_name": row["city_name"],
                      "latitude": row["latitude"],
                      "longitude": row["longitude"],
                      "zip_code": row["zip_code"],
                      "time_zone": row["time_zone"],
                      "isp": row["isp"],
                      "domain": row["domain"],
                      "net_speed": row["net_speed"],
                      "idd_code": row["idd_code"],
                      "area_code": row["area_code"],
                      "weather_station_code": row["weather_station_code"],
                      "weather_station_name": row["weather_station_name"],
                      "mcc": row["mcc"],
                      "mnc": row["mnc"],
                      "mobile_brand": row["mobile_brand"],
                      "elevation": row["elevation"],
                      "usage_type": row["usage_type"]
                },
            }
            id += 1 
            yield doc

def logUpdate(indexName):
    doc = { 
        "document_name" : indexName,
        "updated" : datetime.now()
    }
    resp = es_client.index(index=TIME_LOG_INDEX,id=indexName,document=doc)


def updateIP2Location_IPv6(): 
    print("updating ip2location ipv6")
    helpers.bulk(es_client,import_ip2l_ipv6())
    logUpdate(IP2L_IPV6);



def updateCSV(): 
    print("Updating IP Numbers to Addresses")
    df = pd.read_csv(IP2L_IPV6_DATA)
    df['ip_from'] = df['ip_from'].astype('string')
    df['ip_to'] = df['ip_to'].astype('string')

    df['ip_from'] = df['ip_from'].map(int_to_ipv6)
    df['ip_to'] = (df['ip_to']).map(int_to_ipv6)
    df.to_csv("IPv6_Elastic_Updated.csv",index=False)
    print("Updated numbers to addresses.")
updateCSV()
updateIP2Location_IPv6()