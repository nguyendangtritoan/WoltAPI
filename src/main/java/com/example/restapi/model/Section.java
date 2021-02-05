package com.example.restapi.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Section {
    private String title;
    private List<Restaurant> restaurants;

    public Section(String title, List<Restaurant> restaurants) {
        this.title = title;
        this.restaurants = restaurants;
    }

    public Section() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {

        //Max 10 restaurants in a list
        if(restaurants.size() > 10)
            this.restaurants = restaurants.subList(0,10);
        else
            this.restaurants = restaurants;
    }
}
