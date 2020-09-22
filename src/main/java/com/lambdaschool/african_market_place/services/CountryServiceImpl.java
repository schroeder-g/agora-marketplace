package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Country;
import com.lambdaschool.african_market_place.repositories.CityRepository;
import com.lambdaschool.african_market_place.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "countryService")
public class CountryServiceImpl implements CountryService
{
    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public Country findCountryById(Long countrycode) throws
            ResourceNotFoundException
    {
        return countryRepository.findById(countrycode)
                .orElseThrow(() -> new ResourceNotFoundException("We can't find that"));
    }

    @Override
    public List<Country> findAllCountries()
    {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countries :: add);
        return countries;
    }

    @Override
    public Country save(Country country1) throws
            ResourceNotFoundException
    {
        Country newCountry = new Country();

        if(country1.getCountrycode() != 0)
        {
            newCountry.setCountrycode(country1.getCountrycode());
        }

        newCountry.setName(country1.getName());
        for(City c : country1.getCities() )
        {

            newCountry.getCities().add(cityRepository.findById(c.getCitycode())
                    .orElseThrow(() -> new ResourceNotFoundException("City " + c.getCitycode() + " not found!")));
        }

        return newCountry;
    }
}
