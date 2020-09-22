package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Location;
import com.lambdaschool.african_market_place.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "cityService")
public class CityServiceImpl implements CityService
{
    @Autowired
    CityRepository cityRepository;

    @Override
    public City findCityById(Long cityCode) {
        return cityRepository.findById(cityCode)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find that city!"));
    }

    @Override
    public List<City> findAllCities() {
        List<City> allCities = new ArrayList<>();
        cityRepository.findAll().iterator().forEachRemaining(allCities :: add);

        return allCities;
    }

    @Override
    public City save(City city) {
       City newCity = new City();

       if (city.getCitycode() != 0)
       {
           newCity.setCitycode(city.getCitycode());
       }

       newCity.setCountry(city.getCountry());
       for(Location l : city.getLocations())
       {
        newCity.getLocations().add(l);
       }

       return cityRepository.save(newCity);
    }
}
