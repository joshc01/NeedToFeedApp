package com.cs389team4.needtofeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.databinding.ActivityMainBinding
import com.cs389team4.needtofeed.databinding.FragmentRestaurantBinding
import com.cs389team4.needtofeed.ui.auth.LandingActivity
import com.cs389team4.needtofeed.utils.*

class MainActivity : AppCompatActivity() {
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchBind: FragmentRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        searchBind = FragmentRestaurantBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            initBottomNavigation()
        }

        fetchIdentityId()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initBottomNavigation()
    }

    // Check if user is signed in
    private fun fetchIdentityId() {
        Amplify.Auth.fetchAuthSession(
            { result ->
                val cognitoAuthSession = result as AWSCognitoAuthSession
                when (cognitoAuthSession.identityId.type) {
                    // User signed in
                    AuthSessionResult.Type.SUCCESS ->
                        Log.i(
                            "MainActivity",
                            "User signed in with identityId: " + cognitoAuthSession.identityId.value
                        )
                    // User not signed in
                    AuthSessionResult.Type.FAILURE -> {
                        // Launch welcome screen
                        startActivity(Intent(this, LandingActivity::class.java))
                        finish()
                    }
                }
            },
            { error ->
                // Display error fetching session
                Utils().showMessage(applicationContext, error.toString())
            }
        )
    }

    // Initialize bottom navigation
    private fun initBottomNavigation() {
        val bottomNavigation = binding.navView
        val navGraphIds = listOf(
            R.navigation.nav_graph_home,
            R.navigation.nav_graph_orders,
            R.navigation.nav_graph_profile
        )

        val controller = bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        controller.observe(this, { navController: NavController ->
            setSupportActionBar(binding.toolbar)
            binding.toolbar.setupWithNavController(navController)
        })
        currentNavController = controller
    }

    // Display up navigation when applicable
    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}