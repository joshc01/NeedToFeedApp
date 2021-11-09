package com.cs389team4.needtofeed.models;

import androidx.annotation.NonNull;

import com.amplifyframework.datastore.generated.model.Restaurant;

import com.cs389team4.needtofeed.utils.ViewModel;

public class RestaurantViewModel extends ViewModel<Restaurant> {
    private final Restaurant restaurant;

    public RestaurantViewModel(@NonNull Restaurant model) {
        super(model);

        this.restaurant = model;
    }

    @NonNull
    public String getTitle() {
        return restaurant.getName();
    }

    @NonNull
    public String getCategory() {
        return restaurant.getCategory();
    }

    @NonNull
    public String getImage() {
        return restaurant.getImage();
    }

    @NonNull
    public String getLocation() {
        return restaurant.getLocation();
    }

    @NonNull
    public String getHours() {
        return restaurant.getTimeOpen() + "-" + restaurant.getTimeClose();
    }

    public float getPrice() {
        return 0;
    }

    @NonNull
    public Restaurant getRestaurant() {
        return Restaurant.justId("");
    }
}
