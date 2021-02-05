package com.example.restapi.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Restaurants {
    public Restaurants() {
    }

    private List<Restaurant> restaurants;

    public Restaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
