package com.cs389team4.needtofeed.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.navigation.Navigation;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.cs389team4.needtofeed.MainActivity;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentEditNameBinding;

public class EditNameFragment extends Fragment {
    private FragmentEditNameBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < MainActivity.userAttrs.size(); i++){
            AuthUserAttribute temp = MainActivity.userAttrs.get(i);
            String temp_string = temp.getValue();
            System.out.println(i + " " + temp_string);
        }

        //Grabs the user name from AWS attributes
        AuthUserAttribute name = MainActivity.userAttrs.get(2);
        String name_string = name.getValue();
        TextView name_tv = binding.editNameLbl;
        name_tv.setText(name_string);

        //Saves and updates user attribute name on click
        binding.saveLblContainer.setOnClickListener(v -> {
            AuthUserAttribute newName = new AuthUserAttribute(AuthUserAttributeKey.name(),
                    binding.editNameLbl.getText().toString());
            MainActivity.userAttrs.set(2, newName);
                    Amplify.Auth.updateUserAttribute(newName,
                            result -> Log.i("AuthDemo", "Updated user attribute = "
                            + result.toString()),
                            error -> Log.e("AuthDemo", "Failed to update user attribute.", error)
                    );

            Navigation.findNavController(view).navigate(R.id.navigate_profile);
        }
        );
    }
}
