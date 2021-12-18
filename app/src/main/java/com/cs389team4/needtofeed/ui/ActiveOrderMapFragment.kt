package com.cs389team4.needtofeed.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.fragment.app.Fragment
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.ActivityActiveOrderBinding
import com.cs389team4.needtofeed.utils.LocationPermissionHelper
import com.cs389team4.needtofeed.utils.Utils
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

@MapboxExperimental
class ActiveOrderMapFragment : Fragment(), OnMapClickListener {
    private lateinit var binding: ActivityActiveOrderBinding

    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap

    private lateinit var viewAnnotationManager: ViewAnnotationManager

    private val pointList = CopyOnWriteArrayList<Feature>()
    private var markerId = 0

    private var markerWidth = 0
    private var markerHeight = 0

    private val asyncInflater by lazy {
        AsyncLayoutInflater(requireContext())
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {

        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {

        }
    }

    private companion object {
        const val GEOJSON_SOURCE_ID = "route"
        const val BOUNDS_ID = "bounds"
        const val LAYER_ID = "layer"
        const val MAP_PIN_ID = "pin"

        const val MARKER_ID_PREFIX = "view_annotation_"

        const val SELECTED_ADD_COEF_DP: Float = 15f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityActiveOrderBinding.inflate(inflater, container, false)

        mapView = binding.activeOrderMap

        viewAnnotationManager = mapView.viewAnnotationManager

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.map_pin)
        markerWidth = bitmap.width
        markerHeight = bitmap.height

        mapboxMap = mapView.getMapboxMap().apply {
            loadStyle(
                styleExtension = prepareStyle(Style.MAPBOX_STREETS, bitmap)
            ) {
                addOnMapClickListener(this@ActiveOrderMapFragment)
            }
        }

        locationPermissionHelper = LocationPermissionHelper(WeakReference(activity))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapClick(point: Point): Boolean {
        mapboxMap.queryRenderedFeatures(
            RenderedQueryGeometry(mapboxMap.pixelForCoordinate(point)),
            RenderedQueryOptions(listOf(LAYER_ID), null)
        ) {
            onFeatureClicked(it) { feature ->
                if (feature.id() != null) {
                    viewAnnotationManager.getViewAnnotationByFeatureId(feature.id()!!)?.toggleViewVisibility()
                }
            }
        }
        return true
    }

    private fun onFeatureClicked(expected: Expected<String, List<QueriedFeature>>,
                                 onFeatureClicked: (Feature) -> Unit) {

        if (expected.isValue && expected.value?.size!! > 0) {
            expected.value?.get(0)?.feature?.let { feature ->
                onFeatureClicked.invoke(feature)
            }
        }
    }

    private fun Float.dpToPx() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            requireContext().resources.displayMetrics
        )

    private fun View.toggleViewVisibility() {
        visibility = if (visibility == View.VISIBLE)
            View.GONE else View.VISIBLE
    }

    private fun addMarkerWithId(point: Point): String {
        val currentId = "${MARKER_ID_PREFIX}${(markerId++)}"
        pointList.add(Feature.fromGeometry(point, null, currentId))
        val featureCollection = FeatureCollection.fromFeatures(pointList)
        mapboxMap.getStyle() { style ->
            style.getSourceAs<GeoJsonSource>(GEOJSON_SOURCE_ID)?.featureCollection(featureCollection)
        }
        return currentId
    }

    private fun addAnnotationToMap() {
        Utils.bitmapFromDrawableRes(context, R.drawable.map_pin)?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(0.0, 0.0))
                .withIconImage(it)

            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }

    private fun addViewAnnotation(point: Point, markerId: String) {
        viewAnnotationManager.addViewAnnotation(
            resId = R.layout.map_item_callout_view,
            options = viewAnnotationOptions {
                geometry(point)
                associatedFeatureId(markerId)
                anchor(ViewAnnotationAnchor.BOTTOM)
                allowOverlap(false)
            },
            asyncInflater = asyncInflater
        ) { viewAnnotation ->
            viewAnnotation.visibility = View.GONE

            viewAnnotationManager.updateViewAnnotation(
                viewAnnotation,
                viewAnnotationOptions {
                    offsetY(markerHeight)
                }
            )

        }
    }

    private fun prepareStyle(styleUri: String, bitmap: Bitmap) = style(styleUri) {
        +image(MAP_PIN_ID) {
            bitmap(bitmap)
        }
        +geoJsonSource(GEOJSON_SOURCE_ID) {
            featureCollection(FeatureCollection.fromFeatures(pointList))
        }
        +lineLayer("lineLayer", GEOJSON_SOURCE_ID) {
            lineCap(LineCap.ROUND)
            lineJoin(LineJoin.ROUND)
            lineOpacity(0.7)
            lineWidth(8.0)
            lineColor("#888")
        }
        +symbolLayer(LAYER_ID, GEOJSON_SOURCE_ID) {
            iconImage(MAP_PIN_ID)
            iconAnchor(IconAnchor.BOTTOM)
            iconAllowOverlap(false)
        }
    }

    private fun onMapReady() {
        mapboxMap.setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )

        mapboxMap.loadStyle(
            (
                    style(styleUri = Style.MAPBOX_STREETS) {
                        +geoJsonSource(BOUNDS_ID) {
                            featureCollection(FeatureCollection.fromFeatures(listOf()))
                        }
                        +geoJsonSource(GEOJSON_SOURCE_ID) {
                            url("")
                        }
                        +lineLayer("lineLayer", GEOJSON_SOURCE_ID) {
                            lineCap(LineCap.ROUND)
                            lineJoin(LineJoin.ROUND)
                            lineOpacity(0.7)
                            lineWidth(8.0)
                            lineColor("#888")
                        }
                        Style.OnStyleLoaded {
                            addAnnotationToMap()
                        }
                    })
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun setupGesturesListener() {
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
    }

    private fun setupBounds(bounds: CameraBoundsOptions) {
        mapboxMap.setBounds(bounds)
        showBoundsArea(bounds)
    }

    private fun showBoundsArea(boundsOptions: CameraBoundsOptions) {
        val source = mapboxMap.getStyle()!!.getSource(BOUNDS_ID) as GeoJsonSource
        val bounds = boundsOptions.bounds
        val list = mutableListOf<List<Point>>()

        bounds?.let {
            if (!it.infiniteBounds) {
                val northEast = it.northeast
                val southWest = it.southwest

                val northWest = Point.fromLngLat(southWest.longitude(), northEast.latitude())
                val southEast = Point.fromLngLat(northEast.longitude(), southWest.latitude())

                list.add(
                    mutableListOf(northEast, southEast, southWest, northWest, northEast)
                )
            }
        }
        source.geometry(
            Polygon.fromLngLats(list)
        )
    }
}
