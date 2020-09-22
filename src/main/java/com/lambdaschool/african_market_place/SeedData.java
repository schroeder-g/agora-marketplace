package com.lambdaschool.african_market_place;

// import com.github.javafaker.Faker;
import com.lambdaschool.african_market_place.models.*;
import com.lambdaschool.african_market_place.repositories.CityRepository;
import com.lambdaschool.african_market_place.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Random;


/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData implements CommandLineRunner
{
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
    public void run(String[] args) throws Exception
    {
        listingService.deleteAllListings();
        userService.deleteAll();
        roleService.deleteAll();

        /** ROLES */

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("merchant");
        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);


        /**COUNTRIES */
        Country country1 = new Country("Rwanda");
        /** CITIES */
        //Country country,
        City c1 = new City(country1, "Wakanda");
        country1.getCities().add(c1);
        country1 = countryService.save(country1);
        c1 = cityService.save(c1);


//        City c2 = new City(country1, "My City");
//        country1.getCities().add(c2);
//        c2 = cityService.save(c2);


        /** LOCATIONS */
        //City city, String zip, String address
        Location loc1 = new Location(c1,"123 Example St.", "12345");
        loc1 = locationService.save(loc1);
//        c1.getLocations().add(loc1);

/**       String username, String phonenumber, String email, String fname,
 *          String lname, String password, Location location */
       User admin = new User("username", null, null, null, null, "password", loc1 );
       /** String listingname, String description, double price, int quantity, String category, User user, String imageurl */
       admin.getListings().add(new Listing("New listingasdf", "Listing description", 6.99, 14, "more food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg"));

       admin.getRoles().add(new UserRoles(admin, r1));
       admin = userService.save(admin);

        User vendor = new User( "usernamer", null, null, null, null, "password", loc1 );
        vendor.getRoles().add(new UserRoles(vendor, r3));
//        vendor.getListings().add(new Listing("New listing", "Listing description", 6.99, 14, vendor));
//        vendor.getListings().add(new Listing("New listinggg", "Listing descriptionnnn", 6.50, 14, vendor));
        vendor = userService.save(vendor);
        Listing l1 = new Listing("Eggs Benedict", "Listing description", 6.99, 14, "food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg");
//        listingService.save(l1);
        admin.getListings().add(l1);
        Listing l2 = new Listing("Beef Stew", "Listing description", 6.99, 14, "other food",admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg");
//        listingService.save(l2);
        admin.getListings().add(l2);

        User user = new User("ultimateuser", null, null, null, null, "password", loc1 );
        user.getRoles().add(new UserRoles(user, r2));
        user = userService.save(user);




//        listingService.save(l1);
//        listingService.save(l2);

        /*
        // using JavaFaker create a bunch of regular restaurants
        // https://www.baeldung.com/java-faker
        // https://www.baeldung.com/regular-expressions-java

        Faker nameFaker = new Faker(new Locale("en-US"));

        // this section gets a unique list of names
        Set<String> restNamesSet = new HashSet<>();
        for (int i = 0; i < 100; i++)
        {
            restNamesSet.add(nameFaker.starTrek()
                                     .location() + " Cafe");
        }

        for (String restNames : restNamesSet)
        {
            Restaurant fakeRestaurant = new Restaurant(restNames,
                                                       nameFaker.address()
                                                               .streetAddress(),
                                                       nameFaker.address()
                                                               .cityName(),
                                                       nameFaker.address()
                                                               .stateAbbr(),
                                                       nameFaker.phoneNumber()
                                                               .cellPhone(),
                                                       74);

            int randomNumber = random.nextInt(10) + 1; // random number 1 through 10
            for (int j = 0; j < randomNumber; j++)
            {
                fakeRestaurant.getMenus()
                        .add(new Menu(nameFaker.food()
                                              .dish(),
                                      nameFaker.number()
                                              .randomDouble(2,
                                                            1,
                                                            100),
                                      fakeRestaurant));
            }

            fakeRestaurant.getPayments()
                    .add(pay1);
            restaurantServices.save(fakeRestaurant);
        }
*/
    }
}
