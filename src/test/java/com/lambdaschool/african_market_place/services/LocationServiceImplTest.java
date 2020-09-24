package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.AfricanMarketPlaceApplicationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AfricanMarketPlaceApplicationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocationServiceImplTest {
    @Autowired
    LocationService locationService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllLocations() {
    }

    @Test
    public void findLocationById() {
    }

    @Test
    public void findLocationByUserId() {
    }

    @Test
    public void findLocationByOrderId() {
    }

    @Test
    public void findLocationByCityId() {
    }

    @Test
    public void findCityByCountryName() {
    }

    @Test
    public void save() {
    }

    @Test
    public void deleteLocationById() {
    }

    @Test
    public void deleteAllLocations() {
    }
}