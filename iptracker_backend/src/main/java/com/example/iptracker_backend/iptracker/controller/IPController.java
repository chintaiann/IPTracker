package com.example.iptracker_backend.iptracker.controller;



import com.example.iptracker_backend.iptracker.exception.InvalidIPException;
import com.example.iptracker_backend.iptracker.logger.loggerModel;
import com.example.iptracker_backend.iptracker.models.*;
import com.example.iptracker_backend.iptracker.exception.IPNotFoundException;
import com.example.iptracker_backend.iptracker.models.Greynoise.GreynoiseInfo;
import com.example.iptracker_backend.iptracker.models.Greynoise.Greynoise_IPv4;
import com.example.iptracker_backend.iptracker.models.Greynoise.Greynoise_IPv6;
import com.example.iptracker_backend.iptracker.models.IP2Location.IPv4;
import com.example.iptracker_backend.iptracker.models.IP2Location.IPv6;
import com.example.iptracker_backend.iptracker.models.IP2Location.IpInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.net.InetAddresses;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IPController {
    private ElasticsearchOperations elasticsearchOperations;

    enum logLevel {
        INFO,WARNING,ERROR
    }

    @Autowired
    private com.example.iptracker_backend.iptracker.repo.IPv4Repo IPv4Repo;

    @Autowired
    private com.example.iptracker_backend.iptracker.repo.IPv6Repo IPv6Repo;

    @Autowired
    private com.example.iptracker_backend.iptracker.repo.lastUpdateRepo lastUpdateRepo;

    public IPController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    //log an API call to our database
    public void indexLog(HttpServletRequest request,Principal principal, String logLevel, String message,String queryDetails) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
//        String username = (String) token.getTokenAttributes().get("name");
        String username = principal.getName();
        System.out.println("Checking username: "+ username);
        String url = request.getRequestURI();
        String method = request.getMethod();
        loggerModel log = new loggerModel(username,logLevel,message,method,url,queryDetails);
        elasticsearchOperations.save(log);
    }


//    @GetMapping("/getAll/{pageNumber}")
//    public Page<IPv4> getAll(@PathVariable String pageNumber) {
//        Page<IPv4> response = IPv4Repo.findAll(PageRequest.of(Integer.parseInt(pageNumber), 10));
//        return response;
//    }

    @GetMapping("/getAllUpdates")
    public Map<String,Object> getAll() {
        Map<String, Object> response = new HashMap<>();
        Page<LastUpdate> result = lastUpdateRepo.findAll(PageRequest.of(0, 10));
        response.put("response",result);
        System.out.println(result.getContent());
        return response;
    }

    //query an ip address and checks if it is a valid IPv4/IPv6 address
    public Query IPQuery(String ip_addr,HttpServletRequest request,Principal principal) throws InvalidIPException {
        if (!InetAddresses.isInetAddress(ip_addr)) {
            throw new InvalidIPException("IP address " + ip_addr + " given was not of valid format.", request,principal,ip_addr);
        }
        String first = "{\"match\": { \"ip_range\": \"";
        String last = "\" } } }";
        Query query = new StringQuery(first + ip_addr + last);
        return query;
    }

    public Query GreynoiseQuery(String ip_addr,HttpServletRequest request,Principal principal) throws InvalidIPException {
        if (!InetAddresses.isInetAddress(ip_addr)) {
            throw new InvalidIPException("IP address " + ip_addr + " given was not of valid format.",request,principal,ip_addr);
        }
        String first = "{\"match\": { \"ip\": \"";
        String last = "\" } } }";
        Query query = new StringQuery(first + ip_addr + last);
        return query;
    }



    public Query reverseLookUpQuery(String country_name, String usage_type, String isp, String pageNumber,String pageSize) {
        Criteria criteria = new Criteria("country_name").contains(country_name)
                .and("usage_type").contains(usage_type).and("isp").contains(isp);
        Query query = new CriteriaQuery(criteria);
        query.setPageable(PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        return query;
    }

    public Query reverseLookUpFull(String country_name, String usage_type, String isp) {
        Criteria criteria = new Criteria("country_name").contains(country_name)
                .and("usage_type").contains(usage_type).and("isp").contains(isp);
        Query query = new CriteriaQuery(criteria);
        return query;
    }

    //single query into elasticsearch
    @GetMapping("singleQuery/{protocol}/{source}/{ip}")
    public Map<String, Object> singleQuery(HttpServletRequest request,Principal principal, @PathVariable String source , @PathVariable String protocol, @PathVariable String ip) throws InvalidIPException, IPNotFoundException {
        Map<String, Object> response = new HashMap<>();
        if (protocol.equals("IPv4")) {
            if (source.equals("IP2Location")) {
                Query finalQuery = IPQuery(ip,request,principal);
                SearchHits<IPv4> searchHits = elasticsearchOperations.search(finalQuery, IPv4.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("Sorry, IP was not found in database.",request,principal,ip);
                } else {
                    IpInfo item = searchHits.getSearchHit(0).getContent();
                    item.setEnteredip(ip);
                    response.put("response", item);
                }
            }
            else if (source.equals("Greynoise")) {
                Query finalQuery = GreynoiseQuery(ip,request,principal);
                SearchHits<Greynoise_IPv4> searchHits = elasticsearchOperations.search(finalQuery, Greynoise_IPv4.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("Sorry, IP was not found in database.",request,principal,ip);
                } else {
                    GreynoiseInfo item = searchHits.getSearchHit(0).getContent();
                    response.put("response", item);
                }
            }

        } else if (protocol.equals("IPv6")){
            if (source.equals("IP2Location")) {
                Query finalQuery = IPQuery(ip,request,principal);
                SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("Sorry, IP was not found in database.",request,principal,ip);
                } else {
                    IpInfo item = searchHits.getSearchHit(0).getContent();
                    item.setEnteredip(ip);
                    response.put("response", item);
                }
            }
            else if (source.equals("Greynoise")) {
                Query finalQuery = GreynoiseQuery(ip,request,principal);
                SearchHits<Greynoise_IPv6> searchHits = elasticsearchOperations.search(finalQuery, Greynoise_IPv6.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("Sorry, IP was not found in database.",request,principal,ip);
                } else {
                    GreynoiseInfo item = searchHits.getSearchHit(0).getContent();
                    response.put("response", item);
                }
            }

        }
        indexLog(request,principal,logLevel.INFO.toString(),"",ip);
        return response;
    }


    //bulk query
    @PostMapping("bulkQuery/{protocol}/{source}")
    public Map<String, Object> bulkQuery(HttpServletRequest request, Principal principal,@PathVariable String source, @PathVariable String protocol, @ModelAttribute ipList ipList) throws InvalidIPException, UnknownHostException {
        Map<String, Object> response = new HashMap<>();
        Integer notFound = Integer.valueOf(0);
        if (ipList.count() > 300) {
            throw new InvalidIPException("Please enter a maximum of 300 IP Addresses.",request,principal,"");
        }
        String queryDetails = ipList.toString();
        if (protocol.equals("IPv4")) {
            if (source.equals("IP2Location")){
                List<IPv4> result = new ArrayList<IPv4>();
                List<Query> listQuery = new ArrayList<Query>();
                for (String ipaddress : ipList.getIpList()) {
                    Query finalQuery = IPQuery(ipaddress,request,principal);
                    listQuery.add(finalQuery);
                }
                List<SearchHits<IPv4>> searchHits = elasticsearchOperations.multiSearch(listQuery, IPv4.class);

                for (SearchHits<IPv4> hit : searchHits) {
                    if (hit.getTotalHits() == 0) {
                        //don't show ips which were not found in database.
//                        IPv4 item = new IPv4();
//                        item.setEnteredip(ipList.retrieveTop());
//                        ipList.pop();
//                        result.add(item);
                        ipList.pop();
                        notFound = notFound + 1;
                    } else {
                        IPv4 item2 = hit.getSearchHit(0).getContent();
                        item2.setEnteredip(ipList.retrieveTop());
                        ipList.pop();
                        result.add(item2);
                    }
                }
                response.put("response", result);
                response.put("notFound",notFound);
            }
            else if (source.equals("Greynoise")){
                List<Greynoise_IPv4> result = new ArrayList<Greynoise_IPv4>();
                List<Query> listQuery = new ArrayList<Query>();
                for (String ipaddress : ipList.getIpList()) {
                    Query finalQuery = GreynoiseQuery(ipaddress,request,principal);
                    listQuery.add(finalQuery);
                }
                List<SearchHits<Greynoise_IPv4>> searchHits = elasticsearchOperations.multiSearch(listQuery, Greynoise_IPv4.class);
                for (SearchHits<Greynoise_IPv4> hit : searchHits) {
                    if (hit.getTotalHits() == 0) {
//                        Greynoise_IPv4 item = new Greynoise_IPv4();
//                        item.setIp(ipList.retrieveTop());
//                        ipList.pop();
//                        result.add(item);
                        ipList.pop();
                        notFound = notFound + 1;
                    } else {
                        Greynoise_IPv4 item2 = hit.getSearchHit(0).getContent();
                        item2.setIp(ipList.retrieveTop());
                        ipList.pop();
                        result.add(item2);
                    }
                }
                response.put("response", result);
                response.put("notFound",notFound);

            }

        } else if (protocol.equals("IPv6")) {
            if (source.equals("IP2Location")) {
                List<IPv6> result = new ArrayList<IPv6>();
                for (String ipaddress : ipList.getIpList()) {
                    Query finalQuery = IPQuery(ipaddress,request,principal);
                    SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);

                    if (searchHits.getTotalHits() == 0) {
//                        IPv6 item = new IPv6();
//                        item.setEnteredip(ipaddress);
//                        result.add(item);
                        notFound = notFound + 1;
                    } else {
                        IPv6 item2 = searchHits.getSearchHit(0).getContent();
                        item2.setEnteredip(ipaddress);
                        result.add(item2);
                    }
                }
                response.put("response", result);
                response.put("notFound",notFound);

            }
            else if (source.equals("Greynoise")){
                List<Greynoise_IPv6> result = new ArrayList<Greynoise_IPv6>();
                List<Query> listQuery = new ArrayList<Query>();
                for (String ipaddress : ipList.getIpList()) {
                    Query finalQuery = GreynoiseQuery(ipaddress,request,principal);
                    listQuery.add(finalQuery);
                }
                List<SearchHits<Greynoise_IPv6>> searchHits = elasticsearchOperations.multiSearch(listQuery, Greynoise_IPv6.class);
                for (SearchHits<Greynoise_IPv6> hit : searchHits) {
                    if (hit.getTotalHits() == 0) {
//                        Greynoise_IPv6 item = new Greynoise_IPv6();
//                        item.setIp(ipList.retrieveTop());
//                        ipList.pop();
//                        result.add(item);
                        notFound = notFound + 1;
                    } else {
                        Greynoise_IPv6 item2 = hit.getSearchHit(0).getContent();
                        item2.setIp(ipList.retrieveTop());
                        ipList.pop();
                        result.add(item2);
                    }
                }
                response.put("response", result);
                response.put("notFound",notFound);

            }

        }
        indexLog(request,principal,logLevel.INFO.toString(),"",queryDetails);
        return response;
    }

    //reverse Lookup
    //get back range of IPs based on factors provided
    @PostMapping("reverseLookUp/{protocol}/{pageNumber}/{pageSize}")
    public Map<String, Object> reverseLookUp(HttpServletRequest request, Principal principal,@PathVariable String pageSize,@PathVariable String pageNumber, @PathVariable String protocol, @ModelAttribute filterList filterList) throws InvalidIPException, IPNotFoundException {
        Map<String, Object> response = new HashMap<>();
        filterList.printDetails();
        try {
            if (filterList.getCountry_name().equals("") && filterList.getIsp().equals("") && filterList.getUsage_type().equals("")) {
                throw new InvalidIPException("Please choose at least one filter.",request,principal,"");
            }
            Query finalQuery = reverseLookUpQuery(filterList.getCountry_name(), filterList.getUsage_type(), filterList.getIsp(), pageNumber,pageSize);
            if (protocol.equals("IPv4")) {
                SearchHits<IPv4> searchHits = elasticsearchOperations.search(finalQuery, IPv4.class);
                System.out.println("searchHits: " + searchHits.getTotalHits());
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("No such data exists.",request,principal,filterList.toString());
                }

                SearchPage<IPv4> page = SearchHitSupport.searchPageFor(searchHits, finalQuery.getPageable());
                response.put("response", (Page) SearchHitSupport.unwrapSearchHits(page));


            } else if (protocol.equals("IPv6")) {
                SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("No such data exists.",request,principal,filterList.toString());
                }
                SearchPage<IPv6> page = SearchHitSupport.searchPageFor(searchHits, finalQuery.getPageable());
                response.put("response", (Page) SearchHitSupport.unwrapSearchHits(page));

            }
            indexLog(request,principal,logLevel.INFO.toString(),"",filterList.toString());
            return response;
        } catch (NullPointerException e) {
            throw new IPNotFoundException("Please ensure all fields are entered.",request,principal,filterList.toString());
        }
    }

    //Reverse Lookup API Endpoint to retrieve full lists instead of using pagination
    @PostMapping("reverseLookUp/{protocol}")
    public Map<String, Object> reverseLookUp(HttpServletRequest request,Principal principal,@PathVariable String protocol, @ModelAttribute filterList filterList) throws InvalidIPException, IPNotFoundException {
        Map<String, Object> response = new HashMap<>();
        filterList.printDetails();
        try {
            if (filterList.getCountry_name().equals("") && filterList.getIsp().equals("") && filterList.getUsage_type().equals("")) {
                throw new InvalidIPException("Please choose at least one filter.",request,principal,"");
            }
            Query finalQuery = reverseLookUpFull(filterList.getCountry_name(), filterList.getUsage_type(), filterList.getIsp());
            if (protocol.equals("IPv4")) {
//                SearchHits<IPv4> searchHits = elasticsearchOperations.search(finalQuery, IPv4.class);
//                System.out.println("searchHits: " + searchHits.getTotalHits());
//                if (searchHits.getTotalHits() == 0) {
//                    throw new IPNotFoundException("No such data exists.",request,principal,filterList.toString());
//                }
//                List<IPv4> result = (List<IPv4>) SearchHitSupport.unwrapSearchHits(searchHits);
//                response.put("response", result);

                IndexCoordinates index = IndexCoordinates.of("ip2location_ipv4");
                SearchHitsIterator<IPv4> stream = elasticsearchOperations.searchForStream(finalQuery, IPv4.class,
                        index);
                List<SearchHit<IPv4>> sampleEntities = new ArrayList<>();
                while (stream.hasNext()) {
                    sampleEntities.add(stream.next());
                }

                stream.close();
                System.out.println("Total results:" + String.valueOf(sampleEntities.size()) );
                response.put("response", sampleEntities);





            } else if (protocol.equals("IPv6")) {
                SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("No such data exists.",request,principal,filterList.toString());
                }
                List<IPv6> result = (List<IPv6>) SearchHitSupport.unwrapSearchHits(searchHits);
                response.put("response", result);
            }
            return response;

        } catch (NullPointerException e) {
            throw new IPNotFoundException("Please ensure all fields are entered.",request,principal,filterList.toString());
        }
    }
}