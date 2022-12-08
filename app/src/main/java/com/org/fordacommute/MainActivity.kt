package com.org.fordacommute

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, DirectionsFragment.onButtonListener  {

    //Instantiate Google Map and Current Location
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //Instantiate Drawer Layout
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var startLocation: Location
    private lateinit var destinationLocation: Location

    companion object {

        // Open Navigation Pane function
        fun openDrawer(drawerLayout: DrawerLayout) {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Close Navigation Pane function
        fun closeDrawer(drawerLayout: DrawerLayout) {
            if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        // Redirect to certain activity function
        fun redirectActivity(activity: Activity, aClass: Class<*>) {
            val intent = Intent(activity, aClass)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        }

        // Request Location Permission
        const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // Force screen rotation to just portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Creation of Activity Content
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instantiation of Map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as com.google.android.gms.maps.SupportMapFragment
        mapFragment.getMapAsync(this)

        // Get Current Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Assign value of drawerLayout from activity_about.xml
        drawerLayout = findViewById(R.id.drawer_layout)

        // Current Location button function
        val locationButton = findViewById<Button>(R.id.currentLoc)
        locationButton.setOnClickListener {
            lastLocation
            val currentLatLong = LatLng(lastLocation.latitude, lastLocation.longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 15f))
        }

        val directionsButton = findViewById<Button>(R.id.directionsButton)
        directionsButton.setOnClickListener {
            val showPopup = DirectionsFragment()
            showPopup.show(supportFragmentManager, "showPopup")
        }


    }

    // Open Navigation Pane when Hamburger Menu is clicked
    fun ClickMenu(view: View) {
        openDrawer(drawerLayout)
    }

    // Close Navigation Pane when Logo is clicked
    fun ClickLogo(view: View) {
        closeDrawer(drawerLayout)
    }

    // Close Navigation Pane
    fun ClickHome(view: View) {
        closeDrawer(drawerLayout)
    }

    // Redirect to Fare System Activity
    fun ClickFare(view: View) {
        redirectActivity(this, FareSystem::class.java)
    }

    // Redirect to View Routes Activity
    fun ClickRoutes(view: View) {
        redirectActivity(this, ViewRoutes::class.java)
    }

    // Redirect to Settings Activity
    fun ClickSettings(view: View) {
        redirectActivity(this, Settings::class.java)
    }

    // Redirect to About Activity
    fun ClickAbout(view: View) {
        redirectActivity(this, About::class.java)
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }

    // Override Back Button
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    // Map Settings
    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.uiSettings.isMyLocationButtonEnabled = false
        mMap.uiSettings.isCompassEnabled = true
        mMap.setOnMarkerClickListener(this)

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success: Boolean = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.defaultstyle
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }

        setUpMap()
    }

    private fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) { ActivityCompat.requestPermissions( this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
//                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    private fun updateMarker(currentLatLng: LatLng) {
        val a = MarkerOptions().position(LatLng(currentLatLng.latitude, currentLatLng.longitude))
    }

    override fun onMarkerClick(p0: Marker): Boolean = false

    override fun startCurrentLocation() {
        startLocation =  Location("dummyprovider")
        startLocation.latitude = lastLocation.latitude
        startLocation.longitude = lastLocation.longitude
        val startLatLng = LatLng(startLocation.latitude, startLocation.longitude)
        placeMarkerOnMap(startLatLng)
        updateMarker(startLatLng)
    }

    override fun startMapLocation() {
        startLocation =  Location("dummyprovider")
        startLocation.latitude = 13.76217
        startLocation.longitude = 121.05711
        val startLatLng = LatLng(startLocation.latitude, startLocation.longitude)
        placeMarkerOnMap(startLatLng)
        updateMarker(startLatLng)
    }

    override fun destinationCurrentLocation() {
        destinationLocation =  Location("dummyprovider")
        destinationLocation.latitude = lastLocation.latitude
        destinationLocation.longitude = lastLocation.longitude
        val destinationLatLng = LatLng(destinationLocation.latitude, destinationLocation.longitude)
        placeMarkerOnMap(destinationLatLng)
        updateMarker(destinationLatLng)
    }

    override fun destinationMapLocation() {
        destinationLocation =  Location("dummyprovider")
        destinationLocation.latitude = 13.75502
        destinationLocation.longitude = 121.07055
        val destinationLatLng = LatLng(destinationLocation.latitude, destinationLocation.longitude)
        placeMarkerOnMap(destinationLatLng)
        updateMarker(destinationLatLng)
    }

}

