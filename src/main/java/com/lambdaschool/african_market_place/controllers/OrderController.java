package com.lambdaschool.african_market_place.controllers;

import com.lambdaschool.african_market_place.models.Order;
import com.lambdaschool.african_market_place.services.ListingService;
import com.lambdaschool.african_market_place.services.OrderService;
import com.lambdaschool.african_market_place.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ListingService listingService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> getAllOrders()
    {
        List<Order> allOrders = orderService.findAllOrders();

        return new ResponseEntity<>(allOrders, HttpStatus.OK);

    }

    @GetMapping(value = "/orders/order/{orderid}", produces = "application/json")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderid)
    {
        Order order = orderService.findOrderById(orderid);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //post /orders/order

    //get /orders/orders/:userid

    //put /orders/order/:orderid
    
    //delete /orders/order/:orderid



}
