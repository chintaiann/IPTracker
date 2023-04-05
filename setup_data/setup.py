
import csv 
import pandas as pd 
import socket 
from elasticsearch import Elasticsearch,helpers

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

#cert in docker
CERT_LINK = "/usr/share/elasticsearch/config/certs/ca/ca.crt"
CONTAINER = "https://es01:9200"
#configuration for ES indexing 
es_client = Elasticsearch(
    # "https://localhost:9200", 
    CONTAINER,
    ca_certs=CERT_LINK,
    basic_auth=("elastic","csit1234"))

ipv4_index = "ipv4"
ipv6_index = "ipv6"
response = es_client.indices.create(
    index=ipv4_index,
    body=settings,
    ignore=400 # ignore 400 already exists code
)
print("IPv4 Mapping: " ,response)


response = es_client.indices.create(
    index=ipv6_index,
    body=settings,
    ignore=400 # ignore 400 already exists code
)

print("IPv6 Mapping: " ,response)
#IPv4_Elastic and IPv6_Elastic are dummy data 
#Supposed to be downloaded from ip2location 

def int_to_ipv4(int_ip):
    # Convert the integer to a packed binary format
    packed_ip = int_ip.to_bytes(4, byteorder='big')
    # Convert the packed binary format to a string
    ipv4_addr = socket.inet_ntoa(packed_ip)

    return ipv4_addr
#string of ipv6 integer to ipv6 address
def int_to_ipv6(ip_int):
    # convert input string to an integer
    ip_int = int(ip_int)
    # format the integer as a 128-bit binary string
    bin_str = '{:0128b}'.format(ip_int)
    # split the binary string into groups of 16 bits (2 hextets)
    hextets = [bin_str[i:i+16] for i in range(0, 128, 16)]
    # convert each hextet to its hexadecimal representation
    hex_str = [hex(int(h, 2))[2:].zfill(4) for h in hextets]
    # join the hextets with colons to form the IPv6 address
    return ':'.join(hex_str)



df = pd.read_csv("IPv4_Elastic.csv")
df['ip_from'] = df['ip_from'].map(int_to_ipv4)
df['ip_to'] = df['ip_to'].map(int_to_ipv4)
df.to_csv("IPv4_Elastic_Updated.csv",index=False)

df = pd.read_csv("IPv6_Elastic.csv")
df['ip_from'] = df['ip_from'].astype('string')
df['ip_to'] = df['ip_to'].astype('string')

df['ip_from'] = df['ip_from'].map(int_to_ipv6)
df['ip_to'] = (df['ip_to']).map(int_to_ipv6)
df.to_csv("IPv6_Elastic_Updated.csv",index=False)





#index ipv4 
def generate_ipv4():
    print("Indexing IPv4 documents now")
    with open("IPv4_Elastic_Updated.csv", "r") as fi:
        reader = csv.DictReader(fi, delimiter=",")
        id = 1 
        for row in reader:
            doc = {
                "_index": ipv4_index,
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

helpers.bulk(es_client, generate_ipv4())
#index ipv6
def generate_ipv6():
    print("Indexing IPv6 documents now")

    with open("IPv6_Elastic_Updated.csv", "r") as fi:
        reader = csv.DictReader(fi, delimiter=",")
        id = 1 
        for row in reader:
            doc = {
                "_index": ipv6_index,
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

helpers.bulk(es_client, generate_ipv6())
print("done")


