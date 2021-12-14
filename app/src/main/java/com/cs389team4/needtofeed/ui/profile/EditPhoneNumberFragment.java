package com.cs389team4.needtofeed.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.cs389team4.needtofeed.MainActivity;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentEditPhoneNumberBinding;


public class EditPhoneNumberFragment extends Fragment {
    private FragmentEditPhoneNumberBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditPhoneNumberBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        //Grabs the user name from AWS attributes
        AuthUserAttribute phone = MainActivity.userAttrs.get(5);
        String phone_string = phone.getValue();
        TextView phone_tv = binding.editPhoneNumberLbl;
        phone_tv.setText(phone_string);

        //Saves and updates user attribute name on click
        binding.saveLblContainer.setOnClickListener(v -> {
                    AuthUserAttribute newPhone = new AuthUserAttribute(AuthUserAttributeKey.phoneNumber(),
                            binding.editPhoneNumberLbl.getText().toString());
                    MainActivity.userAttrs.set(5, newPhone);
                    Amplify.Auth.updateUserAttribute(newPhone,
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
