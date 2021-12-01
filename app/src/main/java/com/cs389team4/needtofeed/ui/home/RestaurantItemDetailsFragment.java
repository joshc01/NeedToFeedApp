package com.cs389team4.needtofeed.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Item;
import com.bumptech.glide.Glide;

import com.cs389team4.needtofeed.databinding.FragmentRestaurantItemDetailsBinding;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

public class RestaurantItemDetailsFragment extends Fragment {
    private FragmentRestaurantItemDetailsBinding binding = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantItemDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewItem = binding.itemDetailsImage;
        TextView textViewItemName = binding.itemDetailsName;
        TextView textViewItemPrice = binding.itemDetailsPrice;

        RestaurantItemDetailsFragmentArgs args = RestaurantItemDetailsFragmentArgs.fromBundle(getArguments());

        String itemImage = args.getItemImage();
        Glide.with(view).load(itemImage).centerCrop().into(imageViewItem);

        String itemName = args.getItemName();
        textViewItemName.setText(itemName);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("USD"));
        String itemPrice = format.format(args.getItemPrice());
        textViewItemPrice.setText(itemPrice);

        Button btnAddToCart = binding.itemDetailsAddButton;

        ImageButton btnQuantityAdd = binding.btnQuantityIncrease;
        ImageButton btnQuantityRemove = binding.btnQuantityDecrease;
        TextView textViewQuantity = binding.lblQuantity;

        AtomicInteger quantity = new AtomicInteger(1);

        btnQuantityAdd.setOnClickListener(v -> {
            quantity.getAndIncrement();
            textViewQuantity.setText(quantity.toString());
        });
        btnQuantityRemove.setOnClickListener(v -> {
            if (quantity.get() > 1) {
                quantity.getAndDecrement();
                textViewQuantity.setText(quantity.toString());
            }
        });
    }




}
