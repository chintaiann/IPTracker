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

def import_ipv6_greynoiseJson():
    print("Indexing ipv6 Greynoise documents now")
    f = open(GREYNOISE_IPV6_DATA)
    data = json.load(f)

    id = 1 
    for row in data:

        doc = {
            "_index": GREYNOISE_IPV6 ,
            "_id": id,
            "_source": {
                "metadata": { 
                    "asn": row["metadata"]["asn"] ,
                    "category": row["metadata"]["category"],
                    "country" : row["metadata"]["country"],
                    "city" : row["metadata"]["city"],
                    "organization" : row["metadata"]["organization"],
                    "os" : row["metadata"]["os"],
                    "region" : row["metadata"]["region"],
                },
                "actor" : row["actor"],
                "bot" : row["bot"],
                "classification" : row["classification"],
                "cve" : row["cve"],
                "first_seen" : row["first_seen"],
                "last_seen" : row["last_seen"],
                "ip" : row["ip"],
                "spoofable" : row["spoofable"],
                "seen" : row["seen"],
                "vpn" : row["vpn"],
                "vpn_service" : row["vpn_service"],
                "tags":row["tags"],
                "raw_data" : { 
                    "hassh":row["raw_data"]["hassh"],
                    "ja3" :row["raw_data"]["ja3"],
                    "scan" : row["raw_data"]["scan"],                
                    "web" : {
                        "paths": row["raw_data"]["web"]["paths"],
                        "useragents" : row["raw_data"]["web"]["useragents"]
                        },
                },
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

def updateGreynoise_IPv6(): 
    print("updating greynoise ipv6")
    helpers.bulk(es_client,import_ipv6_greynoiseJson())
    logUpdate(GREYNOISE_IPV6);

updateGreynoise_IPv6()