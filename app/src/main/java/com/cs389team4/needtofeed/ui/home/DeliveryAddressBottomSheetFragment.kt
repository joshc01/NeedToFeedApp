package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import android.view.*
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
            this.dismiss();
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            attributes = attributes.apply {
                gravity = Gravity.BOTTOM
            }
            setDimAmount(0.0f)
        }
    }
}
