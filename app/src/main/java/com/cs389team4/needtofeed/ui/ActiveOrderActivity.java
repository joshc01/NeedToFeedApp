package com.cs389team4.needtofeed.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cs389team4.needtofeed.databinding.ActivityActiveOrderBinding;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

public class ActiveOrderActivity extends AppCompatActivity {
    MapView mapView = null;

    ActivityActiveOrderBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityActiveOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = binding.activeOrderMap;
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
    }
}
