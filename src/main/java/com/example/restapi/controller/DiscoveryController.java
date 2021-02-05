package com.example.restapi.controller;

import com.example.restapi.model.Sections;
import com.example.restapi.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DiscoveryController {

    private RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/discovery")
    public Sections getSectionsResponse(@RequestParam double lat, @RequestParam double lon){
        return restaurantService.getSectionsResponse(lat, lon);
    }

    @GetMapping("/")
    public String greeting(){
        return "Hi, please navigate to /discovery?lat=${}&lon=${}";
    }

}
