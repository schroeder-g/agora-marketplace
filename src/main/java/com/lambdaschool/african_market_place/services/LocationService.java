package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Location;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();

    Location findLocationByUserId(Long userId);

    Location findLocationById(Long locationCode);

    Location findLocationByOrderId(Long orderCode);

    List<Location> findLocationByCityId(Long cityCode);

    List<Location> findLocationByCountryId(Long countryCode);

    List<Location> findLocationByCityName(String name);

    List<Location> findLocationByLikeAddress(String likeAddress);

    List<Location> findLocationByZip(String zip);

    List<City> findCityByCountryName(String countryName);


    Location save(Location location);

    void deleteLocationById(Long locationCode);

    void deleteAllLocations();


}
