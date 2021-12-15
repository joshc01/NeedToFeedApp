package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.MainActivity
import com.cs389team4.needtofeed.databinding.FragmentBottomSheetDeliveryAddressBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeliveryAddressBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetDeliveryAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetDeliveryAddressBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnBottomSheetClose.setOnClickListener {
            Amplify.Auth.updateUserAttribute(
                AuthUserAttribute(AuthUserAttributeKey.address(), "1 pace plaza"),
                { Log.i("AuthDemo", "Updated user attribute = $it") },
                { Log.e("AuthDemo", "Failed to update user attribute.", it) }
            )

            Log.d("DeliveryAddress", MainActivity.userAttrs.toString())
        }
    }
}
