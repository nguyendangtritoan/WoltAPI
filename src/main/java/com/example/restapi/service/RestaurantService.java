package com.example.restapi.service;

import com.example.restapi.model.Sections;

public interface RestaurantService {
    Sections getSectionsResponse(double lat, double lon);
}
