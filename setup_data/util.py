import json
import random
import datetime
from datetime import datetime, timedelta
import string
import os
import csv 
import pandas as pd 
import socket 
from elasticsearch import Elasticsearch,helpers
import ipaddress
from faker import Faker
fake = Faker()
Faker.seed(0)
#string of ipv6 integer to ipv6 address
def int_to_ipv6(ip_int):
    ip_int = int(ip_int)
    bin_str = '{:0128b}'.format(ip_int)
    hextets = [bin_str[i:i+16] for i in range(0, 128, 16)]
    hex_str = [hex(int(h, 2))[2:].zfill(4) for h in hextets]
    return ':'.join(hex_str)

def int_to_ipv4(int_ip):
    packed_ip = int_ip.to_bytes(4, byteorder='big')
    ipv4_addr = socket.inet_ntoa(packed_ip)
    return ipv4_addr




def generate_random_date():
    # Generate a random date between 2010-01-01 and today
    start_date = datetime(2010, 1, 1)
    end_date = datetime.today()
    delta = end_date - start_date
    random_days = random.randint(0, delta.days)
    return start_date + timedelta(days=random_days)


def generate_random_string(length):
    # Generate a random string of given length
    letters = string.ascii_lowercase
    return "".join(random.choice(letters) for _ in range(length))

def generate_random_ip(used_ips):
    # Generate a random IP address in the format xxx.xxx.xxx.xxx
    while True:
        ip = ".".join(str(random.randint(0, 255)) for _ in range(4))
        if ip not in used_ips:
            used_ips.add(ip)
            return ip

def generate_random_ipv6(used_ips):
    # Generate a random IPv6 address in the format xxxx:xxxx:xxxx:xxxx:xxxx:xxxx:xxxx:xxxx
    ip = str(ipaddress.IPv6Address(random.getrandbits(128)))
    while ip in used_ips: 
        ip = str(ipaddress.IPv6Address(random.getrandbits(128)))
    return ip

def generate_ipv4_greynoise_json(used_ips):
    # Generate a random JSON object with the same format as the example data
    data = {
        "actor": random.choice(['APT1 (S3)', 'APT10 (MenuPass Group)', 'APT12 (Dynamite Panda)', 'APT16 (Mustang Panda)', 'APT17 (Deputy Dog)', 'APT18 (Chivy North)', 'APT19 (Codoso Team)', 'APT2 (Double Tap)', 'APT28 (Fancy Bear)', 'APT29 (Cozy Bear)', 'APT3 (Gothic Panda)', 'APT30 (Turkish Crime Family)', 'APT32 (OceanLotus Group)', 'APT33 (Elfin)', 'APT34 (OilRig)', 'APT37 (Reaper)']),
        "bot": random.choice([True, False]),
        "classification": random.choice(["benign", "malware"]),
        "cve": [generate_random_string(8) for _ in range(random.randint(0, 3))],
        "first_seen": generate_random_date().strftime("%Y-%m-%d"),
        "ip": generate_random_ip(used_ips),
        "last_seen": generate_random_date().strftime("%Y-%m-%d"),
        "metadata": {
            "asn": generate_random_string(7),
            "category": random.choice(["business", "education", "government"]),
            "city": fake.city(),
            "country": fake.country(),
            "country_code": fake.country_code(),
            "organization": fake.company(),
            "os": random.choice(["MAC OS","Windows","Linux"]),
            "rdns": "",
            "region": fake.country(),
            "tor": random.choice([True, False]),
        },
        "raw_data": {
            "hassh": [
                {"fingerprint": generate_random_string(32), "port": random.randint(1, 65535)}
                for _ in range(random.randint(0, 3))
            ],
            "ja3": [
                {"fingerprint": generate_random_string(32), "port": random.randint(1, 65535)}
                for _ in range(random.randint(0, 3))
            ],
            "scan": [
                {"port": random.randint(1, 65535), "protocol": random.choice(["TCP", "UDP"])}
                for _ in range(random.randint(0, 3))
            ],
            "web": {
                "paths": ["/" + generate_random_string(5) for _ in range(random.randint(0, 2))],
                "useragents": [
                    fake.chrome() for _ in range(3)
                ],
            },
        },
        "seen": random.choice([True, False]),
        "spoofable": random.choice([True, False]),
        "tags": [
            generate_random_string(6) for _ in range(random.randint(0, 2))
        ],
        "vpn": random.choice([True, False]),
        "vpn_service": "",
    }
    return data

def generate_ipv6_greynoise_json(used_ips):
    # Generate a random JSON object with the same format as the example data

    data = {
        "actor": random.choice(['APT1 (S3)', 'APT10 (MenuPass Group)', 'APT12 (Dynamite Panda)', 'APT16 (Mustang Panda)', 'APT17 (Deputy Dog)', 'APT18 (Chivy North)', 'APT19 (Codoso Team)', 'APT2 (Double Tap)', 'APT28 (Fancy Bear)', 'APT29 (Cozy Bear)', 'APT3 (Gothic Panda)', 'APT30 (Turkish Crime Family)', 'APT32 (OceanLotus Group)', 'APT33 (Elfin)', 'APT34 (OilRig)', 'APT37 (Reaper)']),
        "bot": random.choice([True, False]),
        "classification": random.choice(["benign", "malware"]),
        "cve": [generate_random_string(8) for _ in range(random.randint(0, 3))],
        "first_seen": generate_random_date().strftime("%Y-%m-%d"),
        "ip": generate_random_ipv6(used_ips),
        "last_seen": generate_random_date().strftime("%Y-%m-%d"),
        "metadata": {
            "asn": generate_random_string(7),
            "category": random.choice(["business", "education", "government"]),
            "city": fake.city(),
            "country": fake.country(),
            "country_code": fake.country_code(),
            "organization": fake.company(),
            "os": random.choice(["MAC OS","Windows","Linux"]),
            "rdns": "",
            "region": fake.country(),
            "tor": random.choice([True, False]),
        },
        "raw_data": {
            "hassh": [
                {"fingerprint": generate_random_string(32), "port": random.randint(1, 65535)}
                for _ in range(random.randint(0, 3))
            ],
            "ja3": [
                {"fingerprint": generate_random_string(32), "port": random.randint(1, 65535)}
                for _ in range(random.randint(0, 3))
            ],
            "scan": [
                {"port": random.randint(1, 65535), "protocol": random.choice(["TCP", "UDP"])}
                for _ in range(random.randint(0, 3))
            ],
            "web": {
                "paths": ["/" + generate_random_string(5) for _ in range(random.randint(0, 2))],
                "useragents": [
                    fake.chrome() for _ in range(3)
                ],
            },
        },
        "seen": random.choice([True, False]),
        "spoofable": random.choice([True, False]),
        "tags": [
            generate_random_string(6) for _ in range(random.randint(0, 2))
        ],
        "vpn": random.choice([True, False]),
        "vpn_service": "",
    }
    return data


def generate_ipv4_greynoise_json_list():
    used_ips = set()
    json_list = []
    while len(json_list) < 100000:
        json_list.append(generate_ipv4_greynoise_json(used_ips))
    return json_list

def generate_ipv6_greynoise_json_list(): 
    used_ips=set()
    json_list = []
    while len(json_list) < 100000:
        json_list.append(generate_ipv6_greynoise_json(used_ips))
    return json_list



# generate random greynoise data  
# print("Starting.")
# randomJson = generate_ipv4_greynoise_json_list()
# outfile= open("Greynoise_IPv4.json","w")
# json.dump(randomJson,outfile,indent=6)
# outfile.close()

# randomJson = generate_ipv6_greynoise_json_list()
# outfile= open("Greynoise_IPv6.json","w")
# json.dump(randomJson,outfile,indent=6)
# outfile.close()
# print("Done generating json lists.")
