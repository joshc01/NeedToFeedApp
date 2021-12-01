package com.cs389team4.needtofeed.ui.home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment

import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.FragmentRestaurantMapBinding
import com.cs389team4.needtofeed.utils.LocationPermissionHelper
import com.cs389team4.needtofeed.utils.Utils

import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.observable.eventdata.MapLoadedEventData
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadedListener
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location

import java.lang.ref.WeakReference
import kotlin.random.Random

class RestaurantMapFragment : Fragment(), OnMapLoadedListener, OnMapClickListener {

    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap

    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private var pointAnnotationManager: PointAnnotationManager? = null

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapboxMap.setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapboxMap.pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {

        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private val permissionsRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        permissions.entries.forEach {
            Log.e("RestaurantMapFragment", "${it.key} = ${it.value}")
        }
    }

    private lateinit var binding: FragmentRestaurantMapBinding

    companion object {
        const val ANNOTATION_POINTS_LOADED = 4
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRestaurantMapBinding.inflate(inflater, container, false)

        mapView = binding.restaurantsPickupMap
        mapboxMap = mapView.getMapboxMap()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionsRequestLauncher.launch(
            arrayOf(
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION
            )
        )
        locationPermissionHelper = LocationPermissionHelper(WeakReference(activity))
        locationPermissionHelper.checkPermissions {
            onMapReady()

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mapView.location.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)

        mapView.gestures.removeOnMoveListener(onMoveListener)

        mapboxMap.removeOnMapLoadedListener(this@RestaurantMapFragment)
    }

    override fun onMapClick(point: Point): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMapLoaded(eventData: MapLoadedEventData) {
        pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView).apply {
            Utils.bitmapFromDrawableRes(
                context,
                R.drawable.map_pin
            )?.let {
                val pointOptionsList = mutableListOf<PointAnnotationOptions>()
                for (i in 0 until ANNOTATION_POINTS_LOADED) {
                    pointOptionsList.add(
                        PointAnnotationOptions().withPoint(getPointInBounds()).withIconImage(it)
                    )
                }
                create(pointOptionsList)
            }
            addClickListener(OnPointAnnotationClickListener {
                Utils.showMessage(context, "Point clicked!")
                true
            })
        }
    }

    private fun onMapReady() {
        mapboxMap.setCamera(
            CameraOptions.Builder().zoom(14.0).build()
        )
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
            initLocationComponent()
            setGesturesListener()
            mapView.getMapboxMap().addOnMapLoadedListener(this@RestaurantMapFragment)
        }
    }

    private fun setGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location

        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.mapbox_user_puck_icon
                ),
                shadowImage = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.mapbox_user_icon_shadow
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }

        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        mapView.location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)

        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun getPointInBounds(): Point {
        val bounds: CoordinateBounds = mapboxMap.coordinateBoundsForCamera(mapboxMap.cameraState.toCameraOptions())
        val generator = Random

        val latitude = bounds.southwest.latitude() +
                (bounds.northeast.latitude() - bounds.southwest.latitude()) * generator.nextDouble()

        val longitude = bounds.southwest.longitude() +
                (bounds.northeast.longitude() - bounds.southwest.longitude()) * generator.nextDouble()

        return Point.fromLngLat(longitude, latitude)
    }
}
