package com.cs389team4.needtofeed.ui.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cs389team4.needtofeed.R;
import android.util.Log;
import androidx.navigation.Navigation;
import com.amplifyframework.core.Amplify;
import com.cs389team4.needtofeed.databinding.FragmentEditPasswordBinding;

public class EditPasswordFragment extends Fragment {

    private FragmentEditPasswordBinding binding = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Saves and updates user attribute name on click
        binding.saveLblContainer.setOnClickListener(v -> {
                    Amplify.Auth.updatePassword(returnCurrentPass(), returnNewPass(),
                            () -> Log.i("AuthQuickstart", "Updated password successfully"),
                            error -> Log.e("AuthQuickstart", error.toString()));

                    Navigation.findNavController(view).navigate(R.id.navigate_profile);
                }
        );
    }

    private String returnCurrentPass() {
        String currentPass = binding.currentPasswordContainer.getText().toString();
        return currentPass;
    }

    private String returnNewPass(){
        String newPass = binding.newPasswordContainer.getText().toString();
        return newPass;
    }

}
