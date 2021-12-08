package com.cs389team4.needtofeed.ui.home;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.amplifyframework.datastore.generated.model.Item;
import com.cs389team4.needtofeed.models.ItemViewModel;
import com.cs389team4.needtofeed.utils.ListFragment;
import com.cs389team4.needtofeed.utils.ViewModel;

import java.util.UUID;

public class RestaurantMenuListFragment extends ListFragment<Item> {
    @NonNull
    @Override
    public Item createModel() {
        return Item.justId("");
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
        Navigation.findNavController(getView()).navigate(RestaurantMenuFragmentDirections
                .actionRestaurantMenuFragmentToRestaurantItemDetailsFragment(item.getTitle(), item.getPrice().floatValue(), item.getImage()));

    }

    @Override
    public boolean onLongClick(Item item) {
        return false;
    }

}
