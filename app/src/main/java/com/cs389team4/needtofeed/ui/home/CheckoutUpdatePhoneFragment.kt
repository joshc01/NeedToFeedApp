package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.cs389team4.needtofeed.MainActivity
import com.cs389team4.needtofeed.databinding.FragmentCheckoutUpdatePhoneBinding

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
        binding.checkoutEditPhoneAddInput.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        binding.btnCheckoutEditPhoneAdd.setOnClickListener {
            val strPhone = binding.checkoutEditPhoneAddInput.text.toString()
            var existingNumbers: MutableList<String>

            Amplify.API.query(ModelQuery.get(User::class.java, MainActivity.userDataId),
                {
                    existingNumbers = it.data.phoneNumbers

                    Log.i(TAG, "Query results = ${(it.data as User).id}")
                },
                { Log.e(TAG, "Query failed", it) })

            val user = User.builder()
                .id(MainActivity.userDataId)
                .phoneNumbers(mutableListOf(strPhone))
                //.addresses()
                .build()

            Amplify.API.mutate(ModelMutation.update(user),
                { Log.i(TAG, "Todo with id: ${it.data.id}") },
                { Log.e(TAG, "Create failed", it) }
            )
        }
    }
}
