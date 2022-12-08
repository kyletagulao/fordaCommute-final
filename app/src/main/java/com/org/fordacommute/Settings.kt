package com.org.fordacommute

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

class Settings : AppCompatActivity() {

    // Instantiate Drawer Layout
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        // Force screen rotation to just portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Creation of Activity Content
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Assign value of drawerLayout from activity_about.xml
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

    // Redirect to Main Activity
    fun ClickHome(view: View) {
        MainActivity.redirectActivity(this, MainActivity::class.java)
    }

    // Redirect to Fare System Activity
    fun ClickFare(view: View) {
        MainActivity.redirectActivity(this, FareSystem::class.java)
    }

    // Redirect to View Routes Activity
    fun ClickRoutes(view: View) {
        MainActivity.redirectActivity(this, ViewRoutes::class.java)
    }

    // Close Navigation Pane
    fun ClickSettings(view: View) {
        MainActivity.closeDrawer(drawerLayout)
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