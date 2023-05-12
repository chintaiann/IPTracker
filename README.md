# IPTracker


### Setup 
Download directory and find it in terminal.
Run `docker-compose up --build`


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
* Form Data <br/>
`ipList` <br/> 
e.g `ipList=4.2.80.1,4.2.80.3,1.1.1.1 

* **Success Response** 
`{
    "response": [
        {
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
        },
        {
            "ip": "4.2.80.3",
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
        },
        {
            "ip": "1.1.1.1",
            "ip_from": 0,
            "ip_to": 0,
            "country_code": null,
            "country_name": null,
            "region_name": null,
            "city_name": null,
            "latitude": 0.0,
            "longitude": 0.0,
            "zip_code": 0,
            "time_zone": null,
            "isp": null,
            "domain": null,
            "net_speed": null,
            "idd_code": 0,
            "area_code": null,
            "weather_station_code": null,
            "weather_station_name": null,
            "mcc": null,
            "mnc": null,
            "mobile_brand": null,
            "elevation": 0,
            "usage_type": null
        }
    ]
}`

| Test Cases       | HTTP Status         | Message  |
| ------------- |:-------------:| -----:|
| Invalid IP Format ( at least one )  | 404 | IP is not of valid format.  |
| Not found in Database ( at least one )    | 200 | Response = null for that IP |

----
### Reverse Lookup 
* **URL**
<_/reverseLookUp/{protocol}_>

* **Method** 
`POST`


* **URL Params** <br/>
**Required**  <br/>
`protocol=[IPv4 | IPv6]`<br/>

* **Data Params**
* Form Data <br/>
`country_name` <br/> 
`isp` <br/> 
`usage_type` <br/>

Note: All fields must be included. If empty, put "". 
Isp can be a substring of full ISP name. <br/>
e.g `country_name='United States of America' isp="" usage_type=""`


* **Success Response** 
`{
    "response": [
        "67261440 to 67261695",
        "67260682 to 67260704",
        "67261753 to 67261754",
        "67261759 to 67261764",
        "67261769 to 67261770",]
 }`
