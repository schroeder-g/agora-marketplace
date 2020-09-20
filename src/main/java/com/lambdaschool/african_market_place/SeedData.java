package com.lambdaschool.african_market_place;

// import com.github.javafaker.Faker;
import com.lambdaschool.african_market_place.models.Listing;
import com.lambdaschool.african_market_place.models.Role;
import com.lambdaschool.african_market_place.models.User;
import com.lambdaschool.african_market_place.models.UserRoles;
import com.lambdaschool.african_market_place.services.ListingService;
import com.lambdaschool.african_market_place.services.RoleService;
import com.lambdaschool.african_market_place.services.UserService;
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
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("merchent");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);


       User admin = new User("username", "password");
//        admin.getListings().add(new Listing("New listingasdf", "Listing description", 6.99, true, 14, admin));

       admin.getRoles().add(new UserRoles(admin, r1));
       admin = userService.save(admin);

        User vendor = new User("usernamer", "password");
        vendor.getRoles().add(new UserRoles(vendor, r3));
//        vendor.getListings().add(new Listing("New listing", "Listing description", 6.99, 14, vendor));
//        vendor.getListings().add(new Listing("New listinggg", "Listing descriptionnnn", 6.50, 14, vendor));
        vendor = userService.save(vendor);
        Listing l1 = new Listing("New listingasdf", "Listing description", 6.99, 14, admin);
//        listingService.save(l1);
        admin.getListings().add(l1);
        Listing l2 = new Listing("New listingasdfasdfasf", "Listing description", 6.99, 14, admin);
//        listingService.save(l2);
        admin.getListings().add(l2);

        User user = new User("usernames", "password");
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
