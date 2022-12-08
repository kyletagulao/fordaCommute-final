package com.org.fordacommute

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout

class About : AppCompatActivity() {

    // Instantiate Drawer Layout and Dialog for Profile Popup
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {

        // Force screen rotation to just portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Creation of Activity Content
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Assign value of drawerLayout from activity_about.xml
        drawerLayout = findViewById(R.id.drawer_layout)

        // Button functions for Profile Dialog (Mark Angelo Maligalig)
        val buttonMarkAngelo: Button = findViewById(R.id.markAngelo)
        buttonMarkAngelo.setOnClickListener {

            // Display popup_about_gelo.xml
            val dialogBinding = layoutInflater.inflate(R.layout.popup_about_gelo, null)

            // Assign Dialog in this activity & Set Content of mDialog to dialogBinding
            mDialog = Dialog(this)
            mDialog.setContentView(dialogBinding)

            // Make Dialog closeable when clicked outside of it
            mDialog.setCancelable(true)

            // Set Background of Dialog as Transparent
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // Show Dialog
            mDialog.show()

        // Instantiation of Close Button
        val closeButton = dialogBinding.findViewById<Button>(R.id.popupClose)
            closeButton.setOnClickListener{
                mDialog.dismiss()
            }
        }

        val buttonJoshua: Button = findViewById(R.id.joshuaClemente)
        buttonJoshua.setOnClickListener {

            val dialogBinding = layoutInflater.inflate(R.layout.popup_about_joshua, null)
            mDialog = Dialog(this)
            mDialog.setContentView(dialogBinding)

            mDialog.setCancelable(true)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.show()

            val closeButton = dialogBinding.findViewById<Button>(R.id.popupClose)
            closeButton.setOnClickListener{
                mDialog.dismiss()
            }
        }

        val buttonMarc: Button = findViewById<Button>(R.id.marcFrancis)
        buttonMarc.setOnClickListener {

            val dialogBinding = layoutInflater.inflate(R.layout.popup_about_marc, null)
            mDialog = Dialog(this)
            mDialog.setContentView(dialogBinding)

            mDialog.setCancelable(true)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.show()

            val closeButton = dialogBinding.findViewById<Button>(R.id.popupClose)
            closeButton.setOnClickListener{
                mDialog.dismiss()
            }
        }

        val buttonTristan: Button = findViewById<Button>(R.id.tristanJorge)
        buttonTristan.setOnClickListener {

            val dialogBinding = layoutInflater.inflate(R.layout.popup_about_tristan, null)
            mDialog = Dialog(this)
            mDialog.setContentView(dialogBinding)

            mDialog.setCancelable(true)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.show()

            val closeButton = dialogBinding.findViewById<Button>(R.id.popupClose)
            closeButton.setOnClickListener{
                mDialog.dismiss()
            }
        }

        val buttonMikko: Button = findViewById<Button>(R.id.johnMikko)
        buttonMikko.setOnClickListener {

            val dialogBinding = layoutInflater.inflate(R.layout.popup_about_mikko, null)
            mDialog = Dialog(this)
            mDialog.setContentView(dialogBinding)

            mDialog.setCancelable(true)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.show()

            val closeButton = dialogBinding.findViewById<Button>(R.id.popupClose)
            closeButton.setOnClickListener{
                mDialog.dismiss()
            }
        }

        val buttonGlen: Button = findViewById<Button>(R.id.glenSimbiray)
        buttonGlen.setOnClickListener {

            val dialogBinding = layoutInflater.inflate(R.layout.popup_about_glen, null)
            mDialog = Dialog(this)
            mDialog.setContentView(dialogBinding)

            mDialog.setCancelable(true)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.show()

            val closeButton = dialogBinding.findViewById<Button>(R.id.popupClose)
            closeButton.setOnClickListener{
                mDialog.dismiss()
            }
        }

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

    // Redirect to Fare Guide System Activity
    fun ClickFare(view: View) {
        MainActivity.redirectActivity(this, FareSystem::class.java)
    }

    // Redirect to View Routes Activity
    fun ClickRoutes(view: View) {
        MainActivity.redirectActivity(this, ViewRoutes::class.java)
    }

    // Redirect to Settings Activity
    fun ClickSettings(view: View) {
        MainActivity.redirectActivity(this, Settings::class.java)
    }

    // Close Navigation Pane
    fun ClickAbout(view: View) {
        MainActivity.closeDrawer(drawerLayout)
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