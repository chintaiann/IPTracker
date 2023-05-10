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
    index=GREYNOISE_IPV4,
    ignore=400
)


response = es_client.indices.create( 
    index=TIME_LOG_INDEX,
    ignore=400
)

def import_ipv4_greynoiseJson():
    print("Indexing Greynoise documents now")
    f = open("Greynoise_IPv4.json")
    data = json.load(f)
    id = 1 
    for row in data:

        doc = {
            "_index": GREYNOISE_IPV4 ,
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
        "updated" : datetime.now()
    }
    resp = es_client.index(index=TIME_LOG_INDEX,id=indexName,document=doc)

def updateGreynoise_IPv4(): 
    print("greynoise ipv4 now!")
    helpers.bulk(es_client,import_ipv4_greynoiseJson())
    logUpdate(GREYNOISE_IPV4);


updateGreynoise_IPv4()