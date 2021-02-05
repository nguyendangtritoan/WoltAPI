package com.example.restapi.model;

import org.springframework.stereotype.Component;

@Component
public class Restaurant {

    private String blurhash;
    private String launch_date;
    private double[] location;
    private String name;
    private Boolean online;
    private double popularity;

    public Restaurant(String blurhash, String launch_date, double[] location, String name, Boolean online, double popularity) {
        this.blurhash = blurhash;
        this.launch_date = launch_date;
        this.location = location;
        this.name = name;
        this.online = online;
        this.popularity = popularity;
    }

    public Restaurant(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(String launch_date) {
        this.launch_date = launch_date;
    }

    public String getBlurhash() {
        return blurhash;
    }

    public void setBlurhash(String blurhash) {
        this.blurhash = blurhash;
    }


}
