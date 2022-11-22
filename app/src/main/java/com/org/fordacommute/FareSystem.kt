package com.org.fordacommute

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout

class FareSystem : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fare_system)

        drawerLayout = findViewById(R.id.drawer_layout)

    }

    fun ClickMenu(view: View) {
        MainActivity.openDrawer(drawerLayout)
    }

    fun ClickLogo(view: View) {
        MainActivity.closeDrawer(drawerLayout)
    }

    fun ClickHome(view: View) {
        MainActivity.redirectActivity(this, MainActivity::class.java)
    }

    fun ClickFare(view: View) {
        MainActivity.closeDrawer(drawerLayout)
    }

    fun ClickRoutes(view: View) {
        MainActivity.redirectActivity(this, ViewRoutes::class.java)
    }

    fun ClickSettings(view: View) {
        MainActivity.redirectActivity(this, Settings::class.java)
    }

    fun ClickAbout(view: View) {
        MainActivity.redirectActivity(this, About::class.java)
    }

    override fun onPause() {
        super.onPause()
        MainActivity.closeDrawer(drawerLayout)
    }
}