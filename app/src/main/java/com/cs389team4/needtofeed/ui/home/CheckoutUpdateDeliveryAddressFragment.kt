package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.cs389team4.needtofeed.MainActivity
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.FragmentCheckoutUpdateDeliveryAddressBinding

class CheckoutUpdateDeliveryAddressFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutUpdateDeliveryAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutUpdateDeliveryAddressBinding.inflate(inflater, container, false)

        val recyclerView = binding.checkoutEditAddressList

        var jsonKeyset = arrayOf<String>()
        if (MainActivity.addresses != null) {
            jsonKeyset = MainActivity.addresses!!.keySet().toTypedArray()
        }

        val itemAdapter = MainActivity.addresses?.let { CheckoutUpdateDeliveryAddressAdapter(jsonKeyset, it) }
        recyclerView.adapter = itemAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.checkoutEditActionAddAddressContainer.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                addToBackStack(null)
                replace<CheckoutUpdateAddressFragment>(R.id.checkout_order_details_fragment_container)
            }
        }
    }
}
