package com.org.fordacommute

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val isFirstRun: Boolean = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true)

        if (isFirstRun) {
            Handler().postDelayed({
                val intent = Intent(this@SplashScreen, GettingStarted::class.java)
                startActivity(intent)
            }, 3000)
        } else {
            Handler().postDelayed({
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
            }, 3000)
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit()

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}