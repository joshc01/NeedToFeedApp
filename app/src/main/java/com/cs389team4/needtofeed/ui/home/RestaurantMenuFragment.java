package com.cs389team4.needtofeed.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Order;
import com.bumptech.glide.Glide;

import com.cs389team4.needtofeed.MainActivity;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantMenuBinding;
import com.cs389team4.needtofeed.ui.ActiveOrderActivity;

public class RestaurantMenuFragment extends Fragment {
    private FragmentRestaurantMenuBinding binding = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantMenuBinding.inflate(inflater, container, false);

        if (!MainActivity.getOrderCartExists()) {
            binding.restaurantListContinueCheckout.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewRestaurantName = binding.restaurantMenuRestaurantName;
        TextView textViewRestaurantCategory = binding.restaurantMenuRestaurantCategory;
        ImageView imageViewRestaurantImage = binding.restaurantMenuRestaurantImage;
        AppCompatButton btnViewCart = binding.restaurantListContinueCheckout;

        RestaurantMenuFragmentArgs args = RestaurantMenuFragmentArgs.fromBundle(getArguments());

        String restaurantName = args.getRestaurantName();
        textViewRestaurantName.setText(restaurantName);

        String restaurantCategory = args.getRestaurantCategory();
        textViewRestaurantCategory.setText(restaurantCategory);

        String restaurantImage = args.getRestaurantImage();
        Glide.with(view).load(restaurantImage).fitCenter().into(imageViewRestaurantImage);

        btnViewCart.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ActiveOrderActivity.class));
        });
    }
}
