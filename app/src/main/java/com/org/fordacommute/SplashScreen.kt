package com.org.fordacommute

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private val preferences_name: String = "isFirstTime"

    override fun onCreate(savedInstanceState: Bundle?) {

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        firstTime()

    }

    private fun firstTime() {
        val pref: SharedPreferences = getSharedPreferences(preferences_name, 0)
        if (pref.getBoolean("isFirstTime", true)) {
            Handler().postDelayed({
                val intent = Intent(this@SplashScreen, GettingStarted::class.java)
                startActivity(intent)
            }, 3000)
            pref.edit().putBoolean("isFirstTime", false).commit()
        } else {
            Handler().postDelayed({
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
            }, 3000)
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}