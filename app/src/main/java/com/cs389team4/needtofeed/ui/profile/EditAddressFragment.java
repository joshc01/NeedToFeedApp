package com.cs389team4.needtofeed.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs389team4.needtofeed.databinding.FragmentEditAddressBinding;

public class EditAddressFragment extends Fragment {

    private FragmentEditAddressBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAddressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        //Grabs the user name from AWS attributes
        AuthUserAttribute address = MainActivity.userAttrs.get(2);
        String address_string = address.getValue();
        TextView address_tv = binding.editAddressLbl;
        address_tv.setText(address_string);

        //Saves and updates user attribute name on click
        binding.saveLblContainer.setOnClickListener(v -> {
                    AuthUserAttribute newAddress = new AuthUserAttribute(AuthUserAttributeKey.address(),
                            binding.editAddressLbl.getText().toString());
                    MainActivity.userAttrs.set(2, newAddress);
                    Amplify.Auth.updateUserAttribute(newAddress,
                            result -> Log.i("AuthDemo", "Updated user attribute = "
                                    + result.toString()),
                            error -> Log.e("AuthDemo", "Failed to update user attribute.", error)
                    );

                    Navigation.findNavController(view).navigate(R.id.navigate_profile);
                }
        );

 */
    }
}