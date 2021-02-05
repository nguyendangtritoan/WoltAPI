package com.example.restapi.service.Impl;

import com.example.restapi.model.Restaurant;
import com.example.restapi.model.Restaurants;
import com.example.restapi.model.Section;
import com.example.restapi.model.Sections;
import com.example.restapi.service.RestaurantService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    Section sectionPopular;

    @Autowired
    Section sectionNew;

    @Autowired
    Section sectionNearby;

    @Autowired
    Sections sections;

    public Sections getSectionsResponse(double lat, double lon){

        //Get all 3 Restaurants object for Sections object
        List<Restaurant>[] restaurantsArray = this.getArrayOfRestaurantList(lat,lon);

        //A section for popular restaurant
        //Section sectionPopular = new Section();
        sectionPopular.setTitle("Popular Restaurants");
        sectionPopular.setRestaurants(restaurantsArray[0]);

        //A section for new restaurant
        //Section sectionNew = new Section();
        sectionNew.setTitle("New Restaurants");
        sectionNew.setRestaurants(restaurantsArray[1]);

        //A section for nearby restaurant
        //Section sectionNearby = new Section();
        sectionNearby.setTitle("Nearby Restaurants");
        sectionNearby.setRestaurants(restaurantsArray[2]);

        //Sections object
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(sectionPopular);
        sectionList.add(sectionNew);
        sectionList.add(sectionNearby);

        sections.setSections(sectionList);

        return sections;
    }

    private List<Restaurant>[] getArrayOfRestaurantList(double lat, double lon){
        List<Restaurant> restaurantList = this.getRestaurantDataFromJSONFile().getRestaurants();

        //List of array Restaurant objects (3 lists: Popular, New, Nearby)
        List<Restaurant>[] restaurantsArray = new ArrayList[3];

        //List to hold prior restaurant ( online )
        List<Restaurant> priorRestaurantList = new ArrayList<>();

        //Filter far restaurant and separate between opened and closed
        Iterator<Restaurant> itr = restaurantList.iterator();
        while (itr.hasNext()){
            Restaurant restaurantTemp = itr.next();
            double distance = distance(restaurantTemp,lat,lon);
            if(distance > 1.5){
                itr.remove(); // Remove all the restaurant that more than 1.5 km
            }else {
                if(restaurantTemp.getOnline()){
                    priorRestaurantList.add(restaurantTemp); //Add online restaurant to prior
                    itr.remove(); // remove online restaurant in preserve list
                }
            }
        }

        //SORTED WITH POPULARITY
        Comparator<Restaurant> compareByPopularity = Comparator.comparingDouble(Restaurant::getPopularity).reversed();
        restaurantsArray[0] = filterAndSortResult("popularity",compareByPopularity, priorRestaurantList, restaurantList);

        //SORTED WITH NEW LAUNCHED
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Comparator<Restaurant> compareByLaunchDate = Comparator.comparingLong((Restaurant r1) -> {
            try {
                return format.parse(r1.getLaunch_date()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        }).reversed();
        restaurantsArray[1] = filterAndSortResult("new",compareByLaunchDate, priorRestaurantList, restaurantList);

        //SORTED WITH DISTANCE
        Comparator<Restaurant> compareByDistance = Comparator.comparingDouble((Restaurant r) -> distance(r, lat, lon));
        restaurantsArray[2] = filterAndSortResult("distance",compareByDistance, priorRestaurantList, restaurantList);

        return restaurantsArray;
    }

    //This method is to use to return final result
    private List<Restaurant> filterAndSortResult(
            String optionSort, Comparator<Restaurant> comparator,
            List<Restaurant> priorListParam, List<Restaurant> preserveListParam)
    {
        //Create new List obj to avoid referencing to main list
        ArrayList<Restaurant> priorList = new ArrayList<>(priorListParam);
        ArrayList<Restaurant> preserveList = new ArrayList<>(preserveListParam);

        priorList.sort(comparator);
        if(optionSort.equals("new")){ // Remove launch day which more than 4 months
            priorList = (ArrayList<Restaurant>) priorList
                    .stream()
                    .filter(restaurant -> {
                        LocalDate launchDate = LocalDate.parse(restaurant.getLaunch_date());
                        LocalDate filterDate = LocalDate.now().minusMonths(4);
                        return launchDate.isAfter(filterDate);
                    }).collect(Collectors.toList());
        }

        //Add offline rest if there is capacity
        if(priorList.size() < 10){
            preserveList.sort(comparator); //Sort the preserve list by same comparator of prior list

            Iterator<Restaurant> itr = preserveList.iterator();

            while (priorList.size() < 10 && itr.hasNext()){
                Restaurant restaurant = itr.next();
                priorList.add(restaurant); //Add to prior list if prior list is < 10 objects
            }
        }

        return new ArrayList<>(priorList);
    }

    //Method returns json data from json file
    public Restaurants getRestaurantDataFromJSONFile() {
        //Mapper from JSON to POJO
        ObjectMapper mapper = new ObjectMapper();

        //Read data from json files
        TypeReference<Restaurants> typeReference = new TypeReference<Restaurants>() {};
        Restaurants restaurants = null;
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/restaurants.json");

        try {
            restaurants = mapper.readValue(inputStream,typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    //This method is to calculate distance base on lat and lon
    private double distance(Restaurant restaurant, double lat2, double lon2) {
        double lat1 = restaurant.getLocation()[1];
        double lon1 = restaurant.getLocation()[0];
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);

            return dist * 1.609344 * 60 * 1.1515;
        }
    }

}
