package com.cs389team4.needtofeed.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;

import com.amplifyframework.auth.AuthUserAttribute;
import com.cs389team4.needtofeed.MainActivity;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentAccountBinding;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantBinding;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        AuthUserAttribute name = MainActivity.userAttrs.get(2);
//        String name_string = name.getValue();
//        AppCompatTextView name_tv = binding.profileNameTextView;
//        name_tv.setText(name_string);
    }
}
