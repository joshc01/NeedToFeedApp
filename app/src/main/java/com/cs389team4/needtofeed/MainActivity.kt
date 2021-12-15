package com.cs389team4.needtofeed

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.databinding.ActivityMainBinding
import com.cs389team4.needtofeed.ui.auth.LandingActivity
import com.cs389team4.needtofeed.utils.Utils
import com.cs389team4.needtofeed.utils.setupWithNavController
import androidx.annotation.RequiresApi
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.datastore.generated.model.Order

class MainActivity : AppCompatActivity() {
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG: String = "MainActivity"
        @JvmStatic lateinit var restaurantId: String
        @JvmStatic var orderCartExists: Boolean = false
        @JvmStatic var activeOrderExists: Boolean = false
        @JvmStatic lateinit var userAttrs: MutableList<AuthUserAttribute>
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            initBottomNavigation()
        }

        // Verify user authenticated
        fetchIdentityId()

        // Get user attributes for later use
        getAttributes()
    }

    @RequiresApi(Build.VERSION_CODES.S)
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
                    AuthSessionResult.Type.SUCCESS -> {
                        Log.i(TAG,
                            "User signed in with identityId: " + cognitoAuthSession.identityId.value
                        )

                        checkActiveOrderExists()
                        if (!activeOrderExists) checkOrderCartExists()

                    }
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
                Utils.showMessage(applicationContext, error.toString())
            }
        )
    }

    // Get status of active order cart
    private fun checkOrderCartExists() {
        Amplify.API.query(ModelQuery.list(Order::class.java, Order.IS_EDITABLE.eq(true)),
            { response ->
                // If no active order exists
                if (response.data.items.toString() != "[]") {
                    orderCartExists = true
                }
            })
        { error: ApiException ->
            Log.e("Failure querying order: ", error.message!!)
        }
    }

    private fun checkActiveOrderExists() {
        Amplify.API.query(ModelQuery.list(Order::class.java, Order.IS_ACTIVE.eq(true)),
            { response ->
                if (response.data.items.toString() != "[]") {
                    Utils.showMessage(this, response.toString())
                    activeOrderExists = true
                }
            }
            )
        { error: ApiException ->
            Log.e("Failure querying order: ", error.message!!)
        }
    }

    fun getAttributes() {
        Amplify.Auth.fetchUserAttributes(
            { userAttrs = it },
            { Log.e(TAG, "Failed to fetch user attributes", it) }
        )
    }

    // Initialize bottom navigation
    @RequiresApi(Build.VERSION_CODES.S)
    private fun initBottomNavigation() {
        val bottomNavigation = binding.navView
        val navGraphIds = listOf(
            R.navigation.nav_graph_home,
            R.navigation.nav_graph_orders,
            R.navigation.nav_graph_profile)

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
