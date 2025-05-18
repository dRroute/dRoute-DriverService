package com.droute.driverservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleMapClient", url = "https://maps.googleapis.com")
public interface GoogleMapClient {

    @GetMapping("/maps/api/directions/json")
    String getDirections(
            @RequestParam("origin") String origin,
            @RequestParam("destination") String destination,
            @RequestParam("key") String apiKey,
            @RequestParam("mode") String mode,
            @RequestParam("overview") String overview);

    @GetMapping("/maps/api/geocode/json")
    String getAddress(
            @RequestParam("latlng") String latlng,
            @RequestParam("key") String apiKey);
}
