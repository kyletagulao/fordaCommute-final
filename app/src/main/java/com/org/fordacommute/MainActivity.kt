package com.org.fordacommute

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.maps.android.data.kml.KmlLayer


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as com.google.android.gms.maps.SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val drawerLayout: DrawerLayout = findViewById(R.id.activity_main)

        findViewById<ImageView>(R.id.menu_button).setOnClickListener(View.OnClickListener() {
            drawerLayout.openDrawer(GravityCompat.START)
        })

        val navigationView: NavigationView = findViewById(R.id.navigationViewFragment)

        val navController: NavController = Navigation.findNavController(this, R.id.navigationFragment)

        NavigationUI.setupWithNavController(navigationView, navController)

        val locationButton = findViewById<Button>(R.id.currentLoc)
        locationButton.setOnClickListener {
            lastLocation
            val currentLatLong = LatLng(lastLocation.latitude, lastLocation.longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
        }


        val btn1 = findViewById<Button>(R.id.showRoutes)
        btn1.setOnClickListener {
            mMap
            val alangilan = KmlLayer(mMap, R.raw.bp01, applicationContext)
            alangilan.addLayerToMap()
            val capitolio = KmlLayer(mMap, R.raw.bp03, applicationContext)
            capitolio.addLayerToMap()
            val balagtas = KmlLayer(mMap, R.raw.bp05, applicationContext)
            balagtas.addLayerToMap()
            val balete = KmlLayer(mMap, R.raw.bn01, applicationContext)
            balete.addLayerToMap()
            val lipa = KmlLayer(mMap, R.raw.bn02, applicationContext)
            lipa.addLayerToMap()
        }

    }




    override fun onBackPressed() {
        //super.onBackPressed()
    }

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

    override fun onMarkerClick(p0: Marker): Boolean = false
}

