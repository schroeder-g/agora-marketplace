package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.City;

import java.util.List;

public interface CityService {
    City findCityById(Long cityCode);

    List<City> findAllCities();

    City save(City city);
}
