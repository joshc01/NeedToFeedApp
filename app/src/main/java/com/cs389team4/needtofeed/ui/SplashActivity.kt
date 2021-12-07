package com.cs389team4.needtofeed.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
// import com.amplifyframework.core.Amplify

import com.cs389team4.needtofeed.MainActivity
import com.cs389team4.needtofeed.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Remove comment below to stay signed out
        // Amplify.Auth.signOut({},{})

        // Delaying 3 seconds to start MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
