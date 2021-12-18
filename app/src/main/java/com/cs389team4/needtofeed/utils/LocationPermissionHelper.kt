package com.cs389team4.needtofeed.utils

import android.app.Activity
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.cs389team4.needtofeed.ui.home.PickupMapFragment

import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

import java.lang.ref.WeakReference

class LocationPermissionHelper(val activity: WeakReference<Activity>) {

    private lateinit var permissionsManager: PermissionsManager

    fun checkPermissions(onMapReady: () -> Unit) {
        if (PermissionsManager.areLocationPermissionsGranted(activity.get())) {
            onMapReady()
        } else {
            permissionsManager = PermissionsManager(object: PermissionsListener {
                override fun onExplanationNeeded(permissionsDescription: List<String>) {
                    Utils.showMessage(
                        activity.get(),
                        "Please allow NeedToFeed location access to use this feature."
                    )
                }

                override fun onPermissionResult(permissionGranted: Boolean) {
                    if (permissionGranted) {
                        onMapReady()
                    } else {
                        findNavController(PickupMapFragment()).popBackStack()
                    }
                }
            })

        }
    }

//    fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
}
