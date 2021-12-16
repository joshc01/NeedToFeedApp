package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.databinding.FragmentCheckoutUpdatePhoneBinding
import com.cs389team4.needtofeed.utils.Utils
import com.google.gson.JsonObject

class CheckoutUpdatePhoneFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutUpdatePhoneBinding

    companion object {
        const val TAG = "CheckoutUpdatePhoneFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutUpdatePhoneBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding.checkoutEditPhoneAddInput.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        var userAttrs: JsonObject

        Amplify.Auth.fetchUserAttributes(
            {
                val jsonStrPhone = it[4].value
                Utils.showMessage(context, jsonStrPhone)

                Log.i("CheckoutUpdatePhoneFragment", "User attributes = $it")
            },
            { Log.e("CheckoutUpdatePhoneFragment", "Failed to fetch user attributes", it) }
        )

        binding.btnCheckoutEditPhoneAdd.setOnClickListener {
            val strPhone = binding.checkoutEditPhoneAddInput.text.toString()

            Amplify.Auth.updateUserAttribute(
                AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), strPhone),
                {
                    Log.i(TAG, "Updated user attribute = $it")
                    requireActivity().supportFragmentManager.popBackStack()
                },
                { Log.e(TAG, "Failed to update user attribute.", it) }
            )
        }
    }
}
