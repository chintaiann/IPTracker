# IPTracker
### API Endpoints

### Single Query 
* **URL**
<_/singleQuery/{protocol}/{ip}_>

* **Method** 
`GET`

* **URL Params** <br/>
**Required**  <br/>
`protocol=[IPv4 | IPv6]`<br/>
`ip=[IPv4 address]`

* **Success Response** 
`{
    "response": {
        "ip": "4.2.80.1",
        "ip_from": 67260416,
        "ip_to": 67260671,
        "country_code": "US",
        "country_name": "United States of America",
        "region_name": "Massachusetts",
        "city_name": "Boston",
        "latitude": 42.35843,
        "longitude": -71.05977,
        "zip_code": 2108,
        "time_zone": "-05:00",
        "isp": "Level 3 Communications Inc.",
        "domain": "level3.com",
        "net_speed": "DSL",
        "idd_code": 1,
        "area_code": "617",
        "weather_station_code": "USMA0046",
        "weather_station_name": "Boston",
        "mcc": "-",
        "mnc": "-",
        "mobile_brand": "-",
        "elevation": 14,
        "usage_type": "ISP"
    }
}`


| Test Cases      | HTTP Status         | Message  |
| ------------- |:-------------:| -----:|
| Invalid IP Format | 404 | IP is not of valid format. |
| Not found in Database     | 404      | Sorry, IP was not found in database. |

----
### Bulk Queries 
* **URL**
<_/bulkQuery/{protocol}_>

* **Method** 
`POST`


* **URL Params** <br/>
**Required**  <br/>
`protocol=[IPv4 | IPv6]`<br/>

* **Data Params**
`ipList=[]` <br/> 
e.g `ipList=4.2.80.1,4.2.80.3,1.1.1.1 


| Test Cases       | HTTP Status         | Message  |
| ------------- |:-------------:| -----:|
| Invalid IP Format ( at least one )  | 404 | IP is not of valid format.  |
| Not found in Database ( at least one )    | 200 | Response = null for that IP |

----
### Reverse Lookup 
/getIPFromCountry/{protocol}/{country} 


