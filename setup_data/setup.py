
# import csv 
# import pandas as pd 
# import socket 
# from elasticsearch import Elasticsearch,helpers
# import json
# import random
# import datetime
# from datetime import datetime, timedelta, date
# import string
# from util import *
# from constants import *
# import os

# #cert in docker
# CERT_LINK = "/usr/share/elasticsearch/config/certs/ca/ca.crt"
# CONTAINER = "https://es01:9200"

# #configuration for ES indexing 
# es_client = Elasticsearch(
#     CONTAINER,
#     ca_certs=CERT_LINK,
#     basic_auth=("elastic","csit1234"))

# #CREATE INDEXES IF NOT CREATED YET 
# # response = es_client.indices.create(
# #     index=ip2location_ipv4_index,
# #     body=settings,
# #     ignore=400 # ignore 400 already exists code
# # )


# # response = es_client.indices.create(
# #     index=ip2location_ipv6_index,
# #     body=settings,
# #     ignore=400 # ignore 400 already exists code
# # )

# response = es_client.indices.create(
#     index=greynoise_ipv4_index,
#     ignore=400 # ignore 400 already exists code
# )

# response = es_client.indices.create( 
#     index=greynoise_ipv6_index,
#     ignore=400
# )


# response = es_client.indices.create( 
#     index=time_log_index,
#     ignore=400
# )


# # CONVERT INTEGERS TO IP ADDRESSES 
# # df = pd.read_csv("IPv4_Elastic.csv")
# # df['ip_from'] = df['ip_from'].map(int_to_ipv4)
# # df['ip_to'] = df['ip_to'].map(int_to_ipv4)
# # df.to_csv("IPv4_Elastic_Updated.csv",index=False)

# # df = pd.read_csv("IPv6_Elastic.csv")
# # df['ip_from'] = df['ip_from'].astype('string')
# # df['ip_to'] = df['ip_to'].astype('string')

# # df['ip_from'] = df['ip_from'].map(int_to_ipv6)
# # df['ip_to'] = (df['ip_to']).map(int_to_ipv6)
# # df.to_csv("IPv6_Elastic_Updated.csv",index=False)



# #index ipv4 
# def import_ip2l_ipv4():
#     print("Indexing IPv4 documents now")
#     with open("IPv4_Elastic_Updated.csv", "r") as fi:
#         reader = csv.DictReader(fi, delimiter=",")
#         id = 1 
#         for row in reader:
#             doc = {
#                 "_index": ip2location_ipv4_index,
#                 "_id": id,
#                 "_source": {
#                     "ip_range": { 
#                         "gte": row["ip_from"] ,
#                         "lte": row["ip_to"],
#                     },
#                     "country_code": row["country_code"],
#                       "country_name": row["country_name"],
#                       "region_name": row["region_name"],
#                       "city_name": row["city_name"],
#                       "latitude": row["latitude"],
#                       "longitude": row["longitude"],
#                       "zip_code": row["zip_code"],
#                       "time_zone": row["time_zone"],
#                       "isp": row["isp"],
#                       "domain": row["domain"],
#                       "net_speed": row["net_speed"],
#                       "idd_code": row["idd_code"],
#                       "area_code": row["area_code"],
#                       "weather_station_code": row["weather_station_code"],
#                       "weather_station_name": row["weather_station_name"],
#                       "mcc": row["mcc"],
#                       "mnc": row["mnc"],
#                       "mobile_brand": row["mobile_brand"],
#                       "elevation": row["elevation"],
#                       "usage_type": row["usage_type"]
#                 },
#             }
#             id += 1 
#             yield doc

# # helpers.bulk(es_client, generate_ipv4())
# #index ipv6
# def import_ip2l_ipv6():
#     print("Indexing IPv6 documents now")

#     with open("IPv6_Elastic_Updated.csv", "r") as fi:
#         reader = csv.DictReader(fi, delimiter=",")
#         id = 1 
#         for row in reader:
#             doc = {
#                 "_index": ip2location_ipv6_index,
#                 "_id": id,
#                 "_source": {
#                     "ip_range": { 
#                         "gte": row["ip_from"] ,
#                         "lte": row["ip_to"],
#                     },
#                     "country_code": row["country_code"],
#                       "country_name": row["country_name"],
#                       "region_name": row["region_name"],
#                       "city_name": row["city_name"],
#                       "latitude": row["latitude"],
#                       "longitude": row["longitude"],
#                       "zip_code": row["zip_code"],
#                       "time_zone": row["time_zone"],
#                       "isp": row["isp"],
#                       "domain": row["domain"],
#                       "net_speed": row["net_speed"],
#                       "idd_code": row["idd_code"],
#                       "area_code": row["area_code"],
#                       "weather_station_code": row["weather_station_code"],
#                       "weather_station_name": row["weather_station_name"],
#                       "mcc": row["mcc"],
#                       "mnc": row["mnc"],
#                       "mobile_brand": row["mobile_brand"],
#                       "elevation": row["elevation"],
#                       "usage_type": row["usage_type"]
#                 },
#             }
#             id += 1 
#             yield doc

# # helpers.bulk(es_client, generate_ipv6())

# # generate random greynoise data  
# # randomJson = [generate_greynoise_json() for _ in range(20000)]
# print("Generating JSON lists.")
# randomJson = generate_ipv4_greynoise_json_list()
# outfile= open("Greynoise_IPv4.json","w")
# json.dump(randomJson,outfile,indent=6)
# outfile.close()

# randomJson = generate_ipv6_greynoise_json_list()
# outfile= open("Greynoise_IPv6.json","w")
# json.dump(randomJson,outfile,indent=6)
# outfile.close()
# print("Done generating json lists.")

# #Importing json into ES

# def import_ipv4_greynoiseJson():
#     print("Indexing Greynoise documents now")
#     f = open("Greynoise_IPv4.json")
#     data = json.load(f)

#     id = 1 
#     for row in data:

#         doc = {
#             "_index": greynoise_ipv4_index ,
#             "_id": id,
#             "_source": {
#                 "metadata": { 
#                     "asn": row["metadata"]["asn"] ,
#                     "category": row["metadata"]["category"],
#                     "country" : row["metadata"]["country"],
#                     "city" : row["metadata"]["city"],
#                     "organization" : row["metadata"]["organization"],
#                     "os" : row["metadata"]["os"],
#                     "region" : row["metadata"]["region"],
#                 },
#                 "actor" : row["actor"],
#                 "bot" : row["bot"],
#                 "classification" : row["classification"],
#                 "cve" : row["cve"],
#                 "first_seen" : row["first_seen"],
#                 "last_seen" : row["last_seen"],
#                 "ip" : row["ip"],
#                 "spoofable" : row["spoofable"],
#                 "seen" : row["seen"],
#                 "vpn" : row["vpn"],
#                 "vpn_service" : row["vpn_service"],
#                 "tags":row["tags"],

#                 "raw_data" : { 
#                     "hassh":row["raw_data"]["hassh"],
#                     "ja3" :row["raw_data"]["ja3"],
#                     "scan" : row["raw_data"]["scan"],                
#                     "web" : {
#                         "paths": row["raw_data"]["web"]["paths"],
#                         "useragents" : row["raw_data"]["web"]["useragents"]
#                         },
#                 },
#             },
#         }
#         id += 1 
#         yield doc
# def import_ipv6_greynoiseJson():
#     print("Indexing ipv6 Greynoise documents now")
#     f = open("Greynoise_IPv6.json")
#     data = json.load(f)

#     id = 1 
#     for row in data:

#         doc = {
#             "_index": greynoise_ipv6_index ,
#             "_id": id,
#             "_source": {
#                 "metadata": { 
#                     "asn": row["metadata"]["asn"] ,
#                     "category": row["metadata"]["category"],
#                     "country" : row["metadata"]["country"],
#                     "city" : row["metadata"]["city"],
#                     "organization" : row["metadata"]["organization"],
#                     "os" : row["metadata"]["os"],
#                     "region" : row["metadata"]["region"],
#                 },
#                 "actor" : row["actor"],
#                 "bot" : row["bot"],
#                 "classification" : row["classification"],
#                 "cve" : row["cve"],
#                 "first_seen" : row["first_seen"],
#                 "last_seen" : row["last_seen"],
#                 "ip" : row["ip"],
#                 "spoofable" : row["spoofable"],
#                 "seen" : row["seen"],
#                 "vpn" : row["vpn"],
#                 "vpn_service" : row["vpn_service"],
#                 "tags":row["tags"],
#                 "raw_data" : { 
#                     "hassh":row["raw_data"]["hassh"],
#                     "ja3" :row["raw_data"]["ja3"],
#                     "scan" : row["raw_data"]["scan"],                
#                     "web" : {
#                         "paths": row["raw_data"]["web"]["paths"],
#                         "useragents" : row["raw_data"]["web"]["useragents"]
#                         },
#                 },
#             },
#         }
#         id += 1 
#         yield doc
        
# def logUpdate(indexName):
#     doc = { 
#         "updated" : datetime.now()
#     }
#     resp = es_client.index(index=time_log_index,id=indexName,document=doc)

# def updateGreynoise_IPv4(): 
#     helpers.bulk(es_client,import_ipv4_greynoiseJson())
#     logUpdate(greynoise_ipv4_index);

# def updateGreynoise_IPv6(): 
#     helpers.bulk(es_client,import_ipv6_greynoiseJson())
#     logUpdate(greynoise_ipv6_index);

# def updateIP2Location_IPv4(): 
#     helpers.bulk(es_client,import_ip2l_ipv4())
#     logUpdate(ip2location_ipv4_index);


# def updateIP2Location_IPv6(): 
#     helpers.bulk(es_client,import_ip2l_ipv6())
#     logUpdate(ip2location_ipv6_index);


