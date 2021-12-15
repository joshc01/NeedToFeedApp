package com.cs389team4.needtofeed.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cs389team4.needtofeed.databinding.ActivityActiveOrderBinding

class ActiveOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActiveOrderBinding

    private val bottomSheetFragment = ActiveOrderBottomSheetFragment();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActiveOrderBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bottomSheetFragment.isCancelable = true
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}
