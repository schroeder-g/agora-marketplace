package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Location;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();

    Location findLocationByUserId(Long userId);

    Location findLocationById(Long locationCode);

    Location findLocationByOrderId(Long orderCode);

    Location save(Location location);

    List<Location> findLocationByCityId(Long cityId);

    void deleteLocationById(Long locationCode);

    void deleteAllLocations();


}
