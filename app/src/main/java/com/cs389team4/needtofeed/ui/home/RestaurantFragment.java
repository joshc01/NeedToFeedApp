package com.cs389team4.needtofeed.ui.home;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantBinding;

public class RestaurantFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentRestaurantBinding binding = null;
    private boolean deliveryVisibility = true;
    private boolean pickupVisibility = false;

    public void toggleDeliveryPickup (){
        deliveryVisibility = !deliveryVisibility;
        pickupVisibility = !pickupVisibility;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton btnDelivery = binding.deliveryButton;
        AppCompatButton btnPickup = binding.pickupButton;

        SearchView searchView = binding.searchRestaurants;
        FragmentManager fm = getActivity().getSupportFragmentManager();

        // Add restaurant list to the fragment container onCreate
        FragmentTransaction ftOnCreate = fm.beginTransaction();
        ftOnCreate.add(R.id.restaurant_list_container, RestaurantListFragment.class, null)
                .commit();

        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDelivery.setBackgroundResource(R.drawable.rounded_button_gray);
                btnPickup.setBackgroundResource(R.drawable.rounded_button_transparent);

                if(deliveryVisibility){
                // Do nothing: restaurant list is already showing
                }
                else {
                    FragmentTransaction ftDelivery = fm.beginTransaction();
                    ftDelivery.replace(R.id.restaurant_list_container, RestaurantListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                    toggleDeliveryPickup();
                }
            }
        });

        btnPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPickup.setBackgroundResource(R.drawable.rounded_button_gray);
                btnDelivery.setBackgroundResource(R.drawable.rounded_button_transparent);

                if(pickupVisibility){
                // Do nothing: map is already showing
                }
                else {
                    FragmentTransaction ftPickup = fm.beginTransaction();
                    ftPickup.replace(R.id.restaurant_list_container, RestaurantMapFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
                    toggleDeliveryPickup();
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("", "onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("", "onQueryTextChange: " + newText);
                return false;
            }
        });

        swipeRefreshLayout = binding.swipeRefreshRestaurants;
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(() ->
                swipeRefreshLayout.setRefreshing(false), 1000);
    }
}