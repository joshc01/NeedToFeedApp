package com.cs389team4.needtofeed.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.view.Window
import com.cs389team4.needtofeed.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            exitTransition = Fade()
            enterTransition = Fade()
        }

        // Inflate activity layout
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
