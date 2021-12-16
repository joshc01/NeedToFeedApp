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
import com.cs389team4.needtofeed.databinding.FragmentCheckoutUpdateContactInfoBinding

class CheckoutUpdateContactInfoFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutUpdateContactInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutUpdateContactInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val strName = MainActivity.userAttrs[2].value
        binding.checkoutEditInputName.setText(strName)

        binding.checkoutEditLblAddPhone.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                addToBackStack(null)
                replace<CheckoutUpdatePhoneFragment>(R.id.checkout_order_details_fragment_container)
            }
        }

        binding.checkoutEditContactInfoSubmit.setOnClickListener {
            activity?.finish()
        }
    }
}
