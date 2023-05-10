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


es_client = Elasticsearch(
    CONTAINER,
    ca_certs=CERT_LINK,
    basic_auth=(ELASTIC_USERNAME,ELASTIC_PASSWORD))

response = es_client.indices.create(
    index=IP2L_IPV4,
    body=settings,
    ignore=400 # ignore 400 already exists code
)


response = es_client.indices.create( 
    index=TIME_LOG_INDEX,
    ignore=400
)
def updateCSV(): 
    print("Updating IP Numbers to addresses.")
    df = pd.read_csv(IP2L_IPV4_DATA)
    df['ip_from'] = df['ip_from'].map(int_to_ipv4)
    df['ip_to'] = df['ip_to'].map(int_to_ipv4)
    df.to_csv("IPv4_Elastic_Updated.csv",index=False)
    print("Updated IPv4 CSV from IP Number to Address.")


#index ipv4 
def import_ip2l_ipv4():
    print("Indexing IPv4 documents now")
    with open("IPv4_Elastic_Updated.csv", "r") as fi:
        reader = csv.DictReader(fi, delimiter=",")
        id = 1 
        for row in reader:
            doc = {
                "_index": IP2L_IPV4,
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
        "updated" : datetime.now()
    }
    resp = es_client.index(index=TIME_LOG_INDEX,id=indexName,document=doc)


def updateIP2Location_IPv4(): 
    print("updating ip2location ipv4")
    helpers.bulk(es_client,import_ip2l_ipv4())
    logUpdate(IP2L_IPV4);

updateCSV()
updateIP2Location_IPv4()
