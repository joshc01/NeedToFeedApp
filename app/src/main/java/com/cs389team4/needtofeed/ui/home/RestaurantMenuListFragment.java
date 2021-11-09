package com.cs389team4.needtofeed.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.amplifyframework.datastore.generated.model.Item;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantBinding;
import com.cs389team4.needtofeed.models.ItemViewModel;
import com.cs389team4.needtofeed.utils.ListFragment;
import com.cs389team4.needtofeed.utils.ViewModel;

import java.util.UUID;

public class RestaurantMenuListFragment extends ListFragment<Item> {
    @NonNull
    @Override
    public Item createModel() {
        return Item.builder()
                .title("a title")
                .price(null)
                .restaurant(null)
                .restaurantId(null)
                .image(null)
                .build();
    }

    @NonNull
    @Override
    public Item updateModel(Item model) {
        return model.copyOfBuilder()
                .title(UUID.randomUUID().toString())
                .build();
    }

    @NonNull
    @Override
    public Class<? extends Item> getModelClass() {
        return Item.class;
    }

    @NonNull
    @Override
    public ViewModel<Item> getViewModel(@NonNull Item model) {
        return new ItemViewModel(model);
    }

    @Override
    public void onClick(Item item) {
        Navigation.findNavController(getView()).navigate(R.id.action_restaurantMenuFragment_to_restaurantItemDetailsFragment);
    }

    @Override
    public boolean onLongClick(Item item) {
        return false;
    }
}
