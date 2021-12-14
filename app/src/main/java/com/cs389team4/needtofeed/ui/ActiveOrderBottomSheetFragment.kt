package com.cs389team4.needtofeed.ui

import android.os.Bundle
import android.view.*
import com.cs389team4.needtofeed.databinding.FragmentBottomSheetActiveOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActiveOrderBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetActiveOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetActiveOrderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Set layout for bottom sheet by allowing background press
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)

            attributes = attributes.apply {
                gravity = Gravity.BOTTOM
            }
            setDimAmount(0.0f)
        }
    }
}
