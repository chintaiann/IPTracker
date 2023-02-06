# IPTracker

### Single Query 
/singleQuery/{protocol}/{ip} 
protocol = 'IPv4' | 'IPv6'  
ip = one single IP address 


| Test Cases      | HTTP Status         | Message  |
| ------------- |:-------------:| -----:|
| Invalid IP Format | 404 | IP is not of valid format. |
| Not found in Database     | 404      | Sorry, IP was not found in database. |


### Bulk Queries 
/bulkQuery/{protocol}/{ips} 
protocol = 'IPv4' | 'IPv6'  
ips = list of IP address e.g 1.1.1.1,2.2.2.2,3.3.3.3 


| Test Cases       | HTTP Status         | Message  |
| ------------- |:-------------:| -----:|
| Invalid IP Format ( at least one )  | 404 | IP is not of valid format.  |
| Not found in Database ( at least one )    | 200 | Response = null for that IP |


### Reverse Lookup 
/getIPFromCountry/{protocol}/{country} 


