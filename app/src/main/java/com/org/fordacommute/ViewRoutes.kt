package com.org.fordacommute

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Rect
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getState
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.maps.android.data.kml.KmlLayer


class ViewRoutes : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, BusFragment.onButtonListener, JeepFragment.onButtonListener, TerminalsFragment.onTerminalButtonListener {

    //Instantiate Google Map and Current Location
    lateinit var mMap: GoogleMap
    lateinit var mapFragment: SupportMapFragment
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager



    //Instantiate Drawer Layout
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {

        // Force screen rotation to just portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Creation of Activity Content
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_routes)

        var sheetView: View = findViewById(R.id.routesContainer)

        BottomSheetBehavior.from(sheetView).apply {
            peekHeight = 150
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // Instantiation of Map
        (this.supportFragmentManager.findFragmentById(R.id.mapsRoute) as SupportMapFragment?)?.let {
            it.getMapAsync(this)
        }

        // Get Current Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Assign value of drawerLayout from activity_about.xml
        drawerLayout = findViewById(R.id.drawer_layout)

        tabLayout = findViewById(R.id.routeTabs)
        viewPager = findViewById(R.id.routeViewPager)

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = RouteViewAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        val clearMap: Button = findViewById(R.id.clearMap)
        clearMap.setOnClickListener {
            mMap
            mMap.clear()
        }

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val sheetView: View = findViewById(R.id.routesContainer)
        val mBottomSheetBehavior = BottomSheetBehavior.from(sheetView)
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (mBottomSheetBehavior.state === BottomSheetBehavior.STATE_EXPANDED) {
                val outRect = Rect()
                sheetView.getGlobalVisibleRect(outRect)
                if (!outRect.contains(
                        event.rawX.toInt(),
                        event.rawY.toInt()
                    )
                ) mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        return super.dispatchTouchEvent(event)
    }

    // Open Navigation Pane when Hamburger Menu is clicked
    fun ClickMenu(view: View) {
        MainActivity.openDrawer(drawerLayout)
    }

    // Close Navigation Pane when Logo is clicked
    fun ClickLogo(view: View) {
        MainActivity.closeDrawer(drawerLayout)
    }

    // Close Navigation Pane
    fun ClickHome(view: View) {
        MainActivity.redirectActivity(this, MainActivity::class.java)
    }

    // Redirect to Fare System Activity
    fun ClickFare(view: View) {
        MainActivity.redirectActivity(this, FareSystem::class.java)
    }

    // Redirect to View Routes Activity
    fun ClickRoutes(view: View) {
        MainActivity.closeDrawer(drawerLayout)
    }

    // Redirect to Settings Activity
    fun ClickSettings(view: View) {
        MainActivity.redirectActivity(this, Settings::class.java)
    }

    // Redirect to About Activity
    fun ClickAbout(view: View) {
        MainActivity.redirectActivity(this, About::class.java)
    }

    override fun onPause() {
        super.onPause()
        MainActivity.closeDrawer(drawerLayout)
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
            ) != PackageManager.PERMISSION_GRANTED) { ActivityCompat.requestPermissions( this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.LOCATION_REQUEST_CODE)
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



    // Alangilan Route

    override fun alangilanButton() {
        mMap
        val alangilanLatLng = LatLng(13.76962, 121.06459)
        val alangilan = KmlLayer(mMap, R.raw.bp01, applicationContext)
        alangilan.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(alangilanLatLng,14f))
    }

    // Capitolio Route

    override fun capitolioButton() {
        mMap
        val capitolioLatLng = LatLng(13.76360, 121.06449)
        val capitolio = KmlLayer(mMap, R.raw.bp03, applicationContext)
        capitolio.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(capitolioLatLng,14f))
    }


    // Balagtas Route

    override fun balagtasButton() {
        mMap
        val balagtasLatLng = LatLng(13.7702, 121.06575)
        val balagtas = KmlLayer(mMap, R.raw.bp05, applicationContext)
        balagtas.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(balagtasLatLng,14f))
    }

    // Balete Route

    override fun baleteButton() {
        mMap
        val baleteLatLng = LatLng(13.78333, 121.06031)
        val balete = KmlLayer(mMap, R.raw.bn01, applicationContext)
        balete.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(baleteLatLng,13f))
    }

    // Lipa Route

    override fun lipaButton() {
        mMap
        val lipaLatLng = LatLng(13.84354, 121.10157)
        val lipa = KmlLayer(mMap, R.raw.bn02, applicationContext)
        lipa.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lipaLatLng,11f))
    }

    // Bauan Route

    override fun bauanButton() {
        mMap
        val bauanLatLng = LatLng(13.76868, 121.03489)
        val bauan = KmlLayer(mMap, R.raw.bw02, applicationContext)
        bauan.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bauanLatLng,13f))
    }

    override fun bauanGtButton() {
        mMap
        val bauanGtLatLng = LatLng(13.77118, 121.03828)
        val bauanGt = KmlLayer(mMap, R.raw.bw02gt, applicationContext)
        bauanGt.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bauanGtLatLng,13f))
    }

    // Lemery Route

    override fun lemeryButton() {
        mMap
        val lemeryLatLng = LatLng(13.81496, 120.98586)
        val lemery = KmlLayer(mMap, R.raw.bw04, applicationContext)
        lemery.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lemeryLatLng,11f))
    }

    // Calamba - Lipa Route

    override fun calambaLipaButton() {
        mMap
        val calambaLipaLatLng = LatLng(14.06434, 121.16375)
        val calambaLipa = KmlLayer(mMap, R.raw.calambalipa, applicationContext)
        calambaLipa.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(calambaLipaLatLng,11f))
    }

    override fun calambaBalibagoButton() {
        mMap
        val calambaBalibagoLatLng = LatLng(14.25598, 121.12402)
        val calambaBalibago = KmlLayer(mMap, R.raw.calambabalibagoslex, applicationContext)
        calambaBalibago.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(calambaBalibagoLatLng, 12f))
    }

    override fun balibagoTagaytayButton() {
        mMap
        val balibagoTagaytayLatLng = LatLng(14.22216, 121.03871)
        val balibagoTagaytay = KmlLayer(mMap, R.raw.balibagotagaytay, applicationContext)
        balibagoTagaytay.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(balibagoTagaytayLatLng, 11f))
    }

    override fun jeepneyButton() {
        mMap
        val jeepney = KmlLayer(mMap, R.raw.jeepneyterminals, applicationContext)
        jeepney.addLayerToMap()
    }

    override fun busButton() {
        mMap
        val bus = KmlLayer(mMap, R.raw.busterminals, applicationContext)
        bus.addLayerToMap()
    }

    override fun vanButton() {
        mMap
        val van = KmlLayer(mMap, R.raw.vanterminals, applicationContext)
        van.addLayerToMap()
    }

    override fun nasugbuButton() {
        mMap
        val nasugbuLatLng = LatLng(13.93398, 120.83402)
        val nasugbu = KmlLayer(mMap, R.raw.batangasnasugbu, applicationContext)
        nasugbu.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nasugbuLatLng, 10f))
    }

    override fun alpsPitxButton() {
        mMap
        val alpsPitxLatLng = LatLng(14.16766, 121.10880)
        val alpsPitx = KmlLayer(mMap, R.raw.alpspitx, applicationContext)
        alpsPitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(alpsPitxLatLng, 9f))
    }

    override fun bsePitxButton() {
        mMap
        val bsePitxLatLng = LatLng(14.16766, 121.10880)
        val bsePitx = KmlLayer(mMap, R.raw.bsepitx, applicationContext)
        bsePitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bsePitxLatLng, 9f))
    }

    override fun ceresPitxButton() {
        mMap
        val ceresPitxLatLng = LatLng(14.16766, 121.10880)
        val ceresPitx = KmlLayer(mMap, R.raw.cerespitx, applicationContext)
        ceresPitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ceresPitxLatLng, 9f))
    }

    override fun dltbPitxButton() {
        mMap
        val dltbPitxLatLng = LatLng(14.16766, 121.10880)
        val dltbPitx = KmlLayer(mMap, R.raw.dltbpitx, applicationContext)
        dltbPitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dltbPitxLatLng, 9f))
    }

    override fun goldstarPitxButton() {
        mMap
        val goldstarPitxLatLng = LatLng(14.16766, 121.10880)
        val goldstarPitx = KmlLayer(mMap, R.raw.goldstarpitx, applicationContext)
        goldstarPitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(goldstarPitxLatLng, 9f))
    }

    override fun jamPitxButton() {
        mMap
        val jamPitxLatLng = LatLng(14.16766, 121.10880)
        val jamPitx = KmlLayer(mMap, R.raw.jampitx, applicationContext)
        jamPitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jamPitxLatLng, 9f))
    }

    override fun delarosaPitxButton() {
        mMap
        val delarosaPitxLatLng = LatLng(14.16766, 121.10880)
        val delarosaPitx = KmlLayer(mMap, R.raw.delarosapitx, applicationContext)
        delarosaPitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(delarosaPitxLatLng, 9f))
    }

    override fun rrcgPitxButton() {
        mMap
        val rrcgPitxLatLng = LatLng(14.16766, 121.10880)
        val rrcgPitx = KmlLayer(mMap, R.raw.rrcgpitx, applicationContext)
        rrcgPitx.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rrcgPitxLatLng, 9f))
    }

    override fun japsButton() {
        mMap
        val japsLatLng = LatLng(14.00260, 121.12970)
        val japs = KmlLayer(mMap, R.raw.japs, applicationContext)
        japs.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(japsLatLng, 9f))
    }

    override fun supremeLucenaButton() {
        mMap
        val supremeLucenaLatLng = LatLng(14.16766, 121.10880)
        val supremeLucena = KmlLayer(mMap, R.raw.supremelucena, applicationContext)
        supremeLucena.addLayerToMap()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(supremeLucenaLatLng, 9f))
    }


}

