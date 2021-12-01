package com.cs389team4.needtofeed.models;

import android.util.Log;

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
//        DateTimeFormatter formats = DateTimeFormatter.ofPattern("hh:mm a");
        String timeOpen = restaurant.getTimeOpen().format();
        String timeClose = restaurant.getTimeClose().format();

        return timeOpen + "-" + timeClose;
    }

    public float getPrice() {
        return 0;
    }

    @NonNull
    public String getRestaurant() {
        return "";
    }

}
