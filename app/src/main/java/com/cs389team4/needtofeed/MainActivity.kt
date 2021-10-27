package com.cs389team4.needtofeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.cs389team4.needtofeed.databinding.ActivityMainBinding
import com.cs389team4.needtofeed.utils.setupWithNavController

class MainActivity : AppCompatActivity() {
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            initBottomNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initBottomNavigation()
    }

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

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
