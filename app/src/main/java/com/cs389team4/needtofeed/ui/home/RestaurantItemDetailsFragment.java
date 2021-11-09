package com.cs389team4.needtofeed.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantItemDetailsBinding;

import java.text.NumberFormat;
import java.util.Currency;

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
        Glide.with(view).load(itemImage).into(imageViewItem);

        String itemName = args.getItemName();
        textViewItemName.setText(itemName);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("USD"));
        String itemPrice = format.format(args.getItemPrice());
        textViewItemPrice.setText(itemPrice);
    }
}
