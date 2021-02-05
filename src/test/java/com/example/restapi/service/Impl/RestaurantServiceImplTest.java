package com.example.restapi.service.Impl;

import com.example.restapi.model.Restaurant;
import com.example.restapi.model.Section;
import com.example.restapi.model.Sections;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceImplTest {

    RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();

    @Test
    void getSectionsResponse() {

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section());
        sectionList.add(new Section());
        sectionList.add(new Section());

        Sections mockSections = new Sections(sectionList);

        //Test with far location > than 1.5 km
        Sections sections = restaurantService.getSectionsResponse(61.1709,25.902244);
        assertEquals(sections.getSections().size(),mockSections.getSections().size());// 0 and 0
    }

    @Test
    void getRestaurantDataFromJSONFile() {

        int actualSize = restaurantService.getRestaurantDataFromJSONFile().getRestaurants().size();
        assertEquals(100, actualSize);

        String actualFirstRestaurantName = restaurantService
                .getRestaurantDataFromJSONFile()
                .getRestaurants()
                .get(0)
                .getName();
        assertEquals("Ketchup XL", actualFirstRestaurantName);
    }// Unit test for getting data from json file

    @Test
    void testDistanceCalculate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Create reflection to private method distance
        Method method = RestaurantServiceImpl.class.getDeclaredMethod("distance",Restaurant.class, double.class, double.class);
        method.setAccessible(true);

        double[] location = {24.941244, 60.171987};
        Restaurant restaurant =
                new Restaurant("UKFGw4^KM}$$x@X8N1kB10R+xEWWR8Rlt4o0",
                        "2020-02-23",
                        location,
                        "Ketchup XL",
                        false, 0.30706741877410304);

        double actualDistance = (double) method.invoke(restaurantService,restaurant, 60.1709,24.902244);

        assertEquals(2.16,(double) Math.round(actualDistance*100)/100);
    }//Unit test distance method
}