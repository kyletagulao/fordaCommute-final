package com.org.fordacommute

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout

class FareSystem : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fare_system)

        drawerLayout = findViewById(R.id.drawer_layout)

    }

    // Open Navigation Pane when Hamburger Menu is clicked
    fun ClickMenu(view: View) {
        MainActivity.openDrawer(drawerLayout)
    }

    // Close Navigation Pane when Logo is clicked
    fun ClickLogo(view: View) {
        MainActivity.closeDrawer(drawerLayout)
    }

    // Redirect to Home Activity
    fun ClickHome(view: View) {
        MainActivity.redirectActivity(this, MainActivity::class.java)
    }

    // Close Navigation Pane
    fun ClickFare(view: View) {
        MainActivity.closeDrawer(drawerLayout)
    }

    // Redirect to View Routes Activity
    fun ClickRoutes(view: View) {
        MainActivity.redirectActivity(this, ViewRoutes::class.java)
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

    // Redirect to Main Activity when Back Button is pressed
    override fun onBackPressed() {
        MainActivity.redirectActivity(this, MainActivity::class.java)
    }
}