package com.cs389team4.needtofeed.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.amplifyframework.datastore.generated.model.Item;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.cs389team4.needtofeed.utils.ViewModel;

public class ItemViewModel extends ViewModel<Item> {
    private final Item item;

    public ItemViewModel(@NonNull Item model) {
        super(model);

        this.item = model;
    }

    @NonNull
    public String getTitle() {
        return item.getTitle();
    }

    @NonNull
    public String getCategory() {
        return "";
    }

    public float getPrice() {
        return item.getPrice().floatValue();
    }

    @NonNull
    public String getImage() {
        return item.getImage();
    }

    @NonNull
    public String getLocation() {
        return "";
    }

    @NonNull
    public String getHours() {
        return "";
    }

    @NonNull
    public Restaurant getRestaurant() {
        return item.getRestaurant();
    }

}
