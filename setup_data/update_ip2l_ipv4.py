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
ip2location_ipv4 = "ip2location_ipv4"

es_client = Elasticsearch(
    CONTAINER,
    ca_certs=CERT_LINK,
    basic_auth=(ELASTIC_USERNAME,ELASTIC_PASSWORD))

def add_csv_headers(file_path, headers):
        with open(file_path, 'r', newline='') as file:
            has_headers = csv.Sniffer().has_header(file.read(2048))
        
        if not has_headers:
            print("CSV doesn't have headers. Appending headers now")
            f = pd.read_csv(file_path)
            f.to_csv(file_path, header=headers, index=False)
            print("Headers added successfully.")
        else:
            print("File already has headers.")

        # Copying the remaining rows
headers = ['ip_from', 'ip_to', 'country_code', 'country_name', 'region_name', 'city_name', 'latitude', 'longitude', 'zip_code', 'time_zone', 'isp', 'domain', 'net_speed', 'idd_code', 'area_code', 'weather_station_code', 'weather_station_name', 'mcc', 'mnc', 'mobile_brand', 'elevation', 'usage_type']
def updateCSV(): 
    add_csv_headers(IP2L_IPV4_DATA,headers)
    print("Updating IP Numbers to addresses.")

    df = pd.read_csv(IP2L_IPV4_DATA)
    df['ip_from'] = df['ip_from'].map(int_to_ipv4)
    df['ip_to'] = df['ip_to'].map(int_to_ipv4)
    df.to_csv("IPv4_Elastic_Updated.csv",index=False)
    print("Updated IPv4 CSV from IP Number to Address.")


#index ipv4 
def import_ip2location_ipv4():
    with open("IPv4_Elastic_Updated.csv", "r") as fi:
        reader = csv.DictReader(fi, delimiter=",")
        id = 1 
        for row in reader:
            doc = {
                "_index": ip2location_ipv4,
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
    timeNow = str(datetime.now())
    doc = { 
        "document_name" : indexName,
        "updated" : datetime.now()
    }
    print("Updating logs : {0} being updated at {1}".format(indexName,timeNow))
    resp = es_client.index(index=TIME_LOG_INDEX,id=indexName,document=doc)

def updateIP2Location_IPv4(): 
    print("Indexing IP2Location IPv4 now.")
    helpers.bulk(es_client,import_ip2location_ipv4())
    logUpdate(ip2location_ipv4);
    print("Done indexing IP2Location IPv6 Data.")

# updateCSV()
# updateIP2Location_IPv4()

if __name__ == "__main__":
    updateCSV()
    updateIP2Location_IPv4()