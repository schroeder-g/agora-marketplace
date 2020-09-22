package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Location;
import com.lambdaschool.african_market_place.models.Order;
import com.lambdaschool.african_market_place.models.User;
import com.lambdaschool.african_market_place.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "locationService")
public class LocationServiceImpl implements LocationService
{
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Location> getAllLocations() throws
            ResourceNotFoundException
    {
        List<Location> allLocations = new ArrayList<>();
         locationRepository.findAll().iterator().forEachRemaining(allLocations :: add);

         return allLocations;
    }

    @Override
    public Location findLocationById(Long locationCode) throws
            ResourceNotFoundException
    {
        Location location = locationRepository.findById(locationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Location " + locationCode + " not found!"));
        return location;
    }

    @Override
    public Location findLocationByUserId(Long userId) throws
            ResourceNotFoundException
    {
        User locationUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found!"));

        return locationUser.getLocation();
    }

    @Override
    public Location findLocationByOrderId(Long orderCode) throws
            ResourceNotFoundException
    {
        Order locationOrder = orderRepository.findById(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Order " + orderCode + " not found!"));
        return locationOrder.getLocation();
    }

    @Override
    public List<Location> findLocationByCityId(Long cityCode) throws
            ResourceNotFoundException
    {
        List<Location> cityLocations = new ArrayList<>();

        for (Location l: cityRepository.findById(cityCode).get().getLocations())
        {
            cityLocations.add(l);
        }

        return cityLocations;
    }

    @Override
    public List<Location> findLocationByCountryId(Long countryCode) {
        return null;
    }

    @Override
    public List<Location> findLocationByCityName(String name) {
        return null;
    }

    @Override
    public List<Location> findLocationByLikeAddress(String likeAddress) {
        return null;
    }

    @Override
    public List<Location> findLocationByZip(String zip) {
        return null;
    }

    @Override
    public List<City> findCityByCountryName(String countryName) {
        return null;
    }

    @Override
    public Location save(Location location) {
        return null;
    }

    @Override
    public void deleteLocationById(Long locationCode) {

    }

    @Override
    public void deleteAllLocations() {

    }
}
