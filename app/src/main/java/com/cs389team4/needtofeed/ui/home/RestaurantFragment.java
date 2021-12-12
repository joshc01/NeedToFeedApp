package com.cs389team4.needtofeed.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cs389team4.needtofeed.MainActivity;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RestaurantFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FusedLocationProviderClient fusedLocationClient;

    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentRestaurantBinding binding = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false);

        if (!MainActivity.getOrderCartExists()) {
            binding.restaurantMenuContinueCheckout.setVisibility(View.GONE);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 991);
            // here to request the missing permissions, and then overriding

            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            // Got nullable last known location
            if (location != null) {
                // Logic to handle location object
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String address = addresses.get(0).getThoroughfare();

                    binding.restaurantsLblSelectedAddress.setText(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton btnViewCart = binding.restaurantMenuContinueCheckout;

        binding.toggleOrderMode.setOnClickListener(v -> {
            if (binding.toggleOrderMode.isChecked()) {
                Navigation.findNavController(view).navigate(R.id.action_navigate_home_to_restaurantMapFragment);
                binding.toggleOrderMode.setChecked(false);
            }
        });

        //-------------------- Action to view your cart from restaurant list -----------------------
        btnViewCart.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), OrderCartActivity.class)));

        swipeRefreshLayout = binding.swipeRefreshRestaurants;
        swipeRefreshLayout.setOnRefreshListener(this);

        binding.restaurantsDeliveryAddressContainer.setOnClickListener(v -> {
            BottomSheetDeliveryAddressFragment bottomSheet = new BottomSheetDeliveryAddressFragment();
            bottomSheet.show(requireActivity().getSupportFragmentManager(), bottomSheet.getTag());
        });
    }

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(() ->
                swipeRefreshLayout.setRefreshing(false), 1000);
    }
}
