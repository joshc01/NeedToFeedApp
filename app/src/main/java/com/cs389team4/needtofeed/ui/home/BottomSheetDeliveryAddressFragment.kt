package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cs389team4.needtofeed.databinding.FragmentBottomSheetDeliveryAddressBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDeliveryAddressFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetDeliveryAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetDeliveryAddressBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnBottomSheetClose.setOnClickListener {

        }
    }
}
