package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cs389team4.needtofeed.databinding.FragmentCheckoutUpdateDeliveryAddressBinding

class CheckoutUpdateDeliveryAddressFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutUpdateDeliveryAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutUpdateDeliveryAddressBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
