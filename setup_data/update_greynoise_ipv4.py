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
import sys 

es_client = Elasticsearch(
    CONTAINER,
    ca_certs=CERT_LINK,
    basic_auth=(ELASTIC_USERNAME,ELASTIC_PASSWORD))

def import_ipv4_greynoiseJson(file_path):
    #use this if json is not structured yet 
    with open(file_path, 'r') as file:
        json_data = file.read()

        # Split the data into individual JSON strings
        json_strings = json_data.split('\n')

        # Convert each JSON string to a Python dictionary and append to the list
        json_list = []
        for json_str in json_strings:
            if json_str.strip() != '':
                json_list.append(json.loads(json_str))

#use this 2 lines if json is already structured in a list 
    # f = open(file_path)
    # json_list = json.load(f)

    for row in json_list:
        doc = {
            '_op_type' : 'update',
            'doc_as_upsert' : True,
            "_index": GREYNOISE_IPV4,
            "_id": row["ip"],
            #changed _source to doc for update 
            "doc": {
                "metadata": { 
                    "asn": row["metadata"]["asn"] ,
                    "category": row["metadata"]["category"],
                    "country" : row["metadata"]["country"],
                    "country_code" : row["metadata"]["country_code"],
                    "city" : row["metadata"]["city"],
                    "organization" : row["metadata"]["organization"],
                    "os" : row["metadata"]["os"],
                    "region" : row["metadata"]["region"],
                    "rdns" : row["metadata"]["rdns"],
                    "tor" : row["metadata"]["tor"],
                    "source_country" : row["metadata"]["source_country"],
                    "source_country_code" : row["metadata"]["source_country_code"],
                    "destination_countries" : row["metadata"]["destination_countries"],
                    "destination_country_codes" : row["metadata"]["destination_country_codes"],
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
                        "paths": None or row["raw_data"]["web"]["paths"],
                        "useragents" : None or row["raw_data"]["web"]["useragents"]
                    },
                },
            },
        }
        yield doc

def logUpdate(indexName):
    timeNow = str(datetime.now())
    doc = { 
        "document_name" : indexName,
        "updated" : datetime.now()
    }
    print("Updating logs : {0} being updated at {1}".format(indexName,timeNow))
    resp = es_client.index(index=TIME_LOG_INDEX,id=indexName,document=doc)

def deleteFile(file_path):
    try: 
        os.remove(file_path)
        print("File {0} was removed.".format(str(file_path))) 
    except OSError as e: 
        print("Error encountered when trying to delete file {0}".format(str(file_path)))
    
def updateGreynoise_IPv4(file_path):
    print("Indexing Greynoise IPv4 from {0}".format(str(file_path))) 
    helpers.bulk(es_client,import_ipv4_greynoiseJson(file_path))
    logUpdate(GREYNOISE_IPV4);
    print("Updated Greynoise IPv4.")
    deleteFile(file_path)




if __name__ == "__main__":
    if len(sys.argv) < 2: 
        print("Usage: python update_greynoise_ipv4.py file_path ")
        sys.exit(1)
    file_path = GREYNOISE_IPV4_FOLDER+str(sys.argv[1])
    print("Python script working on: " + file_path)

    updateGreynoise_IPv4(file_path)
