package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.AfricanMarketPlaceApplicationTest;
import com.lambdaschool.african_market_place.models.Role;
import com.lambdaschool.african_market_place.models.User;
import com.lambdaschool.african_market_place.models.UserRoles;
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
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;
    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
    }
    @After
    public void tearDown() throws
            Exception
    {
    }
    @Test
    public void findUserById() {
        assertEquals("username", userService.findUserById(6)
                .getUsername());
    }

    @Test
    public void findByNameContaining() {
        assertEquals(4, userService.findByNameContaining("a")
                .size());
    }

    @Test
    public void findAll() {
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void Y_delete() {
        userService.delete(9);
        assertEquals(3, userService.findAll().size());
    }

    @Test
    public void findByName() {
        userService.findByName("username");
        assertEquals("username", userService.findByName("username"));
    }

    @Test
    public void save() {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        User u2 = new User("tiger", "ILuvMath!");
        u2.getRoles().add(new UserRoles(u2, r2));
            User saveU2 = userService.save(u2);
        System.out.println("*** DATA ***");
        System.out.println(saveU2);
        System.out.println("*** DATA ***");
        assertEquals("tiger", userService.findByName("tiger").getUsername());
    }

    @Test
    public void update() {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        User u2 = new User("tiger", "ILuvMath!");
        u2.getRoles().add(new UserRoles(u2, r2));
        User updatedu2 = userService.update(u2, 7);
        System.out.println("*** DATA ***");
        System.out.println(updatedu2);
        System.out.println("*** DATA ***");
        assertEquals("tiger", userService.findByName("tiger").getUsername());
    }

    @Test
    public void Z_deleteAll() {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }

//    @Test
//    public void A_becomeAMerchant() {
//        User user = new User("TestMerchant", "password");
//        user = userService.save(user);
//        userService.becomeAMerchant("TestMerchant");
//        assertEquals(true, user.getRoles().contains("merchant"));
//    }
}