package com.lambdaschool.african_market_place;

import com.github.javafaker.Faker;
import com.lambdaschool.african_market_place.models.*;
import com.lambdaschool.african_market_place.repositories.CityRepository;
import com.lambdaschool.african_market_place.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;


/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData implements CommandLineRunner {
    /**
     * Connects the Restaurant Service to this process
     */
    @Autowired
    private ListingService listingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * A Random generator is needed to randomly generate faker data.
     */
    private Random random = new Random();

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws Exception {
        listingService.deleteAllListings();
        userService.deleteAll();
        roleService.deleteAll();
        locationService.deleteAllLocations();


        /** ROLES */

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("merchant");
        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        /**COUNTRIES */
        Country country1 = new Country("Rwanda");
        country1 = countryService.save(country1);

        City c1 = new City(country1, "Wakanda");
        c1 = cityService.save(c1);

        country1.getCities().add(c1);

        /** CITIES */
        //Country country,
//        City c1 = new City(country1, "Wakanda");
//        country1.getCities().add(c1);
//        country1 = countryService.save(country1);


//        City c2 = new City(country1, "My City");
//        country1.getCities().add(c2);
//        c2 = cityService.save(c2);


        /** LOCATIONS */
        //City city, String zip, String address
        Location loc1 = new Location(c1, "123 Example St.", "12345 address");
//        loc1 = locationService.save(loc1);
        c1.getLocations().add(loc1);


        User admin = new User("username", null, null, null, null, "password", loc1);
        admin.getListings().add(new Listing("New listingasdf", "Listing description", 6.99, 14, "more food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg"));

        admin.getRoles().add(new UserRoles(admin, r1));
        admin = userService.save(admin);

        User vendor = new User("usernamer", null, null, null, null, "password", loc1);
        vendor.getRoles().add(new UserRoles(vendor, r3));

        vendor = userService.save(vendor);
        Listing l1 = new Listing("Eggs Benedict", "Listing description", 6.99, 14, "food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg");

        admin.getListings().add(l1);
        Listing l2 = new Listing("Beef Stew", "Listing description", 6.99, 14, "other food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg");

        admin.getListings().add(l2);

        User user = new User("ultimateuser", null, null, null, null, "password", loc1);
        user.getRoles().add(new UserRoles(user, r2));
//
//        Order o1 = new Order(loc1);
////        OrderItem oi1 = new OrderItem(o1, l1, 5);
////        oi1 = orderItemService.save(oi1);
//        o1.setUser(user);


//        o1.getOrderItems().add(new OrderItem(o1, l1, 5));
//        o1 = orderService.save(o1);
//        user.getOrders().add(o1);
//        o1 = orderService.save(o1);

        user = userService.save(user);


//        listingService.save(l1);
//        listingService.save(l2);


/**  using JavaFaker create a bunch of regular restaurants
 https://www.baeldung.com/java-faker
 https://www.baeldung.com/regular-expressions-java
 */

        Faker nameFaker = new Faker(new Locale("en-US"));

        // this section gets a unique list of namesry
        Set<String> countryNamesSet = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            countryNamesSet.add(nameFaker.country().name());
        }

        Set<Country> countrySet = new HashSet<>();
        for (String countryName : countryNamesSet) {
            Country fakeCountry = new Country(countryName);
            fakeCountry = countryService.save(fakeCountry);

            int randomNumber = random.nextInt(3) + 1; // random number 1 through 10
            for (int j = 0; j < randomNumber; j++) {
                fakeCountry.getCities()

                        .add(new City(fakeCountry, nameFaker.gameOfThrones()
                                .city()));

                for (City c : fakeCountry.getCities()) {
                    int randomLocNumber = random.nextInt(3) + 1; // random number 1 through 10
                    for (int k = 0; k < randomLocNumber; k++) {
                        c.getLocations().add(new Location(c,
                                nameFaker.address().zipCode(),
                                nameFaker.address().streetAddress()));
                        for (Location l : c.getLocations()) {
                            int randomUserNumber = random.nextInt(3) + 1; // random number 1 through 10
                            for (int z = 0; z < randomUserNumber; z++) ;
                            {
                                User fakeUser = new User(
                                        nameFaker.company().name(),
                                        nameFaker.phoneNumber().phoneNumber(),
                                        nameFaker.internet().emailAddress(),
                                        nameFaker.name().firstName(),
                                        nameFaker.name().lastName(),
                                        "password",
                                        l);

                                fakeUser = userService.save(fakeUser);
                            }

                        }
                    }
                }
            }
        }

        List<User> userList = userService.findAll();
        for (User u : userList)
        {

            int randomListingNumber = random.nextInt(3) + 1; // random number 1 through 3

            for (int y = 0; y < randomListingNumber; y++){
                Listing newListing = new Listing(nameFaker.pokemon().name(),
                        nameFaker.lorem().sentence(),
                        nameFaker.number().randomDouble(2, 1, 1000),
                        nameFaker.number().randomDigitNotZero(),
                        nameFaker.commerce().material(),
                        u,
                        "https://ca.slack-edge.com/ESZCHB482-W015P677M1T-d9f28327bb26-512");

                u.getRoles().add(new UserRoles(u, r2));
                newListing = listingService.save(newListing);

            }
        }
    }
}
