package com.example.iptracker_backend.iptracker.controller;



import com.example.iptracker_backend.iptracker.exception.InvalidIPException;
import com.example.iptracker_backend.iptracker.models.*;
import com.example.iptracker_backend.iptracker.exception.IPNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.http.ResponseEntity;
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

    public IPController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Autowired
    private com.example.iptracker_backend.iptracker.repo.IPv4Repo IPv4Repo;

    @Autowired
    private com.example.iptracker_backend.iptracker.repo.IPv6Repo IPv6Repo;




    @GetMapping("/getAll/{pageNumber}")
    public Page<IPv4> getAll(@PathVariable String pageNumber) {
        Page<IPv4> response = IPv4Repo.findAll(PageRequest.of(Integer.parseInt(pageNumber), 10));
        return response;
    }

    //query an ip address and checks if it is a valid IPv4/IPv6 address
    public Query IPQuery(String ip_addr) throws InvalidIPException {
        if (!InetAddresses.isInetAddress(ip_addr)) {
            throw new InvalidIPException("IP address " + ip_addr + " given was not of valid format.");
        }
        String first = "{\"match\": { \"ip_range\": \"";
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
    @GetMapping("singleQuery/{protocol}/{ip}")
    public Map<String, Object> singleQuery(@PathVariable String protocol, @PathVariable String ip) throws InvalidIPException, IPNotFoundException {

        Map<String, Object> response = new HashMap<>();
        Query finalQuery = IPQuery(ip);
        if (protocol.equals("IPv4")) {
            SearchHits<IPv4> searchHits = elasticsearchOperations.search(finalQuery, IPv4.class);
            if (searchHits.getTotalHits() == 0) {
                throw new IPNotFoundException("Sorry, IP was not found in database.");
            } else {
                IpInfo item = searchHits.getSearchHit(0).getContent();
                item.setEnteredip(ip);
                response.put("response", item);
            }
        } else {
            SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);
            if (searchHits.getTotalHits() == 0) {
                throw new IPNotFoundException("Sorry, IP was not found in database.");
            } else {
                IpInfo item = searchHits.getSearchHit(0).getContent();
                item.setEnteredip(ip);
                response.put("response", item);
            }
        }


        return response;
    }


    //bulk query
    @PostMapping("bulkQuery/{protocol}")
    public Map<String, Object> bulkQuery(@PathVariable String protocol, @ModelAttribute ipList ipList) throws InvalidIPException, UnknownHostException {
        Map<String, Object> response = new HashMap<>();
        if (protocol.equals("IPv4")) {
            List<IPv4> result = new ArrayList<IPv4>();
            List<Query> listQuery = new ArrayList<Query>();
            for (String ipaddress : ipList.getIpList()) {
                Query finalQuery = IPQuery(ipaddress);
                listQuery.add(finalQuery);
            }
            List<SearchHits<IPv4>> searchHits = elasticsearchOperations.multiSearch(listQuery, IPv4.class);
            for (SearchHits<IPv4> hit : searchHits) {
                if (hit.getTotalHits() == 0) {
                    IPv4 item = new IPv4();
                    item.setEnteredip(ipList.retrieveTop());
                    ipList.pop();
                    result.add(item);
                } else {
                    IPv4 item2 = hit.getSearchHit(0).getContent();
                    item2.setEnteredip(ipList.retrieveTop());
                    ipList.pop();
                    result.add(item2);
                }
            }
            response.put("response", result);
        } else {
            List<IPv6> result = new ArrayList<IPv6>();
            for (String ipaddress : ipList.getIpList()) {
                Query finalQuery = IPQuery(ipaddress);
                SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);

                if (searchHits.getTotalHits() == 0) {
                    IPv6 item = new IPv6();
                    item.setEnteredip(ipaddress);
                    result.add(item);
                } else {
                    IPv6 item2 = searchHits.getSearchHit(0).getContent();
                    item2.setEnteredip(ipaddress);
                    result.add(item2);
                }
            }
            response.put("response", result);
        }
        return response;
    }

    //reverse Lookup
    //get back range of IPs based on factors provided
    @PostMapping("reverseLookUp/{protocol}/{pageNumber}/{pageSize}")
    public Map<String, Object> reverseLookUp(@PathVariable String pageSize,@PathVariable String pageNumber, @PathVariable String protocol, @ModelAttribute filterList filterList) throws InvalidIPException, IPNotFoundException {
        Map<String, Object> response = new HashMap<>();
        filterList.printDetails();
        try {
            if (filterList.getCountry_name().equals("") && filterList.getIsp().equals("") && filterList.getUsage_type().equals("")) {
                throw new InvalidIPException("Please choose at least one filter.");
            }
            Query finalQuery = reverseLookUpQuery(filterList.getCountry_name(), filterList.getUsage_type(), filterList.getIsp(), pageNumber,pageSize);
            if (protocol.equals("IPv4")) {
                SearchHits<IPv4> searchHits = elasticsearchOperations.search(finalQuery, IPv4.class);
                System.out.println("searchHits: " + searchHits.getTotalHits());
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("No such data exists.");
                }

                SearchPage<IPv4> page = SearchHitSupport.searchPageFor(searchHits, finalQuery.getPageable());
                response.put("response", (Page) SearchHitSupport.unwrapSearchHits(page));


            } else if (protocol.equals("IPv6")) {
                SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("No such data exists.");
                }
                SearchPage<IPv6> page = SearchHitSupport.searchPageFor(searchHits, finalQuery.getPageable());
                response.put("response", (Page) SearchHitSupport.unwrapSearchHits(page));

            }

            //check if any result is empty since result of intersection will be 0


            return response;
        } catch (NullPointerException e) {
            throw new IPNotFoundException("Please ensure all fields are entered. If empty, use " + '"' + '"' + ".");
        }
    }

    //Reverse Lookup API Endpoint to retrieve full lists instead of using pagination
    @PostMapping("reverseLookUp/{protocol}")
    public Map<String, Object> reverseLookUp(@PathVariable String protocol, @ModelAttribute filterList filterList) throws InvalidIPException, IPNotFoundException {
        Map<String, Object> response = new HashMap<>();
        filterList.printDetails();
        try {
            if (filterList.getCountry_name().equals("") && filterList.getIsp().equals("") && filterList.getUsage_type().equals("")) {
                throw new InvalidIPException("Please choose at least one filter.");
            }
            Query finalQuery = reverseLookUpFull(filterList.getCountry_name(), filterList.getUsage_type(), filterList.getIsp());
            if (protocol.equals("IPv4")) {
                SearchHits<IPv4> searchHits = elasticsearchOperations.search(finalQuery, IPv4.class);
                System.out.println("searchHits: " + searchHits.getTotalHits());
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("No such data exists.");
                }
                List<IPv4> result = (List<IPv4>) SearchHitSupport.unwrapSearchHits(searchHits);
                response.put("response", result);
            } else if (protocol.equals("IPv6")) {
                SearchHits<IPv6> searchHits = elasticsearchOperations.search(finalQuery, IPv6.class);
                if (searchHits.getTotalHits() == 0) {
                    throw new IPNotFoundException("No such data exists.");
                }
                List<IPv6> result = (List<IPv6>) SearchHitSupport.unwrapSearchHits(searchHits);
                response.put("response", result);
            }
            return response;

        } catch (NullPointerException e) {
            throw new IPNotFoundException("Please ensure all fields are entered. If empty, use " + '"' + '"' + ".");
        }
    }
}