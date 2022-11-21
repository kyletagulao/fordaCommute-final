package com.org.fordacommute

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.libraries.maps.SupportMapFragment


class MainActivity : AppCompatActivity()  {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
//        mapFragment.getMapAsync(OnMapReadyCallback { this })

//        mapFragment.getMapAsync(OnMapReadyCallback {
//            googleMap = googleMap
//        })

//        val layer = KmlLayer(googleMap, R.raw.bp01, applicationContext)
//        layer.addLayerToMap()

    }


    override fun onBackPressed() {
        //super.onBackPressed()
    }
}