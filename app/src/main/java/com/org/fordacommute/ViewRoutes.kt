package com.org.fordacommute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

class ViewRoutes : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_routes)

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
        MainActivity.redirectActivity(this, FareSystem::class.java)
    }

    fun ClickRoutes(view: View) {
        MainActivity.closeDrawer(drawerLayout)
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