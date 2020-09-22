package com.lambdaschool.african_market_place.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orderitems")
public class OrderItem {

    //#region fields/constructors
    @ManyToOne
    @JoinColumn(name = "ordercode")
    @JsonIgnoreProperties(value = "order")
    private Order order;

    @ManyToMany
    @JoinTable(name = "orderitems",
            joinColumns = @JoinColumn(name="order_code"),
            inverseJoinColumns = @JoinColumn(name = "listing_code"))
    private List<Listing> Listings;

    @Column(nullable = false)
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(int quantity) {
        this.quantity = quantity;
    }

    //#endregion

    //#region getters/setters

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setListings(List<Listing> listings) {
        Listings = listings;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //#endregion
}
