package com.cs389team4.needtofeed.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.cs389team4.needtofeed.MainActivity;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentEditEmailBinding;


public class EditEmailFragment extends Fragment {

    private FragmentEditEmailBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Grabs the user name from AWS attributes
        AuthUserAttribute email = MainActivity.userAttrs.get(3);
        String email_string = email.getValue();
        TextView email_tv = binding.editEmailLbl;
        email_tv.setText(email_string);

        //Saves and updates user attribute name on click
        binding.saveLblContainer.setOnClickListener(v -> {
                    AuthUserAttribute newEmail = new AuthUserAttribute(AuthUserAttributeKey.email(),
                            binding.editEmailLbl.getText().toString());
                    MainActivity.userAttrs.set(3, newEmail);
                    Amplify.Auth.updateUserAttribute(newEmail,
                            result -> Log.i("AuthDemo", "Updated user attribute = "
                                    + result.toString()),
                            error -> Log.e("AuthDemo", "Failed to update user attribute.", error)
                    );

                    Navigation.findNavController(view).navigate(R.id.navigate_profile);
                }
        );
    }
}
