package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.Listing;
import com.lambdaschool.african_market_place.repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "listingService")
public class ListingServiceImpl implements ListingService{

    @Autowired
    ListingRepository listRepo;

    @Autowired
    UserService userService;

    @Override
    public List<Listing> findAll() {
        List<Listing> listings = new ArrayList<>();
        listRepo.findAll().iterator().forEachRemaining(listings::add);
        return listings;
    }

    @Override
    public Listing findById(long listingid) throws ResourceNotFoundException {
        Listing listing = listRepo.findById(listingid)
                .orElseThrow(() -> new ResourceNotFoundException("Listing " + listingid + " not found!"));
        return listing;
    }

    @Override
    public Listing save(Listing listing) throws ResourceNotFoundException
    {
        Listing newListing = new Listing();

        if (listing.getListingid() != 0)
        {
            listRepo.findById(listing.getListingid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + listing.getListingid() + " not found!"));
            newListing.setListingid(listing.getListingid());
        }

        newListing.setDescription(listing.getDescription());
        newListing.setListingname(listing.getListingname());
        newListing.setPrice(listing.getPrice());
        newListing.setQuantity(listing.getQuantity());
        newListing.setUser(listing.getUser());

        return listRepo.save(newListing);
    }

    @Override
    public Listing update(long listingid, Listing listing)
    {
       Listing currentListing = findById(listingid);

        if(listing.getDescription() != null){
            currentListing.setDescription(listing.getDescription());
        }

        if(listing.getListingname() != null){
            currentListing.setListingname(listing.getListingname());
        }

        if(listing.getPrice() != 0){
            currentListing.setPrice(listing.getPrice());
        }

        if(listing.getQuantity() != 0){
            currentListing.setQuantity(listing.getQuantity());
        }

        if(listing.getUser() != null){
            currentListing.setUser(listing.getUser());
        }

        return listRepo.save(currentListing);
    }

    @Override
    public void delete(long listingid) {
        listRepo.deleteById(listingid);
    }

    @Override
    public void deleteAllListings() {
        listRepo.deleteAll();
    }
}
