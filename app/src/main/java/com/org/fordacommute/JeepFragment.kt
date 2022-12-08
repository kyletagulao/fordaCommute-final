package com.org.fordacommute

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

open class JeepFragment : Fragment() {

    // Interface for connectivity between Fragment and Activity

    interface onButtonListener {
        fun alangilanButton()
        fun capitolioButton()
        fun balagtasButton()
        fun baleteButton()
        fun lipaButton()
        fun bauanButton()
        fun bauanGtButton()
        fun lemeryButton()
        fun calambaLipaButton()
        fun calambaBalibagoButton()
        fun balibagoTagaytayButton()
    }

    lateinit var alangilanListener: onButtonListener
    lateinit var capitolioListener: onButtonListener
    lateinit var balagtasListener: onButtonListener
    lateinit var baleteListener: onButtonListener
    lateinit var lipaListener: onButtonListener
    lateinit var bauanListener: onButtonListener
    lateinit var bauanGtListener: onButtonListener
    lateinit var lemeryListener: onButtonListener
    lateinit var calambaLipaListener: onButtonListener
    lateinit var calambaBalibagoListener: onButtonListener
    lateinit var balibagoTagaytayListener: onButtonListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
            alangilanListener = context as onButtonListener
            capitolioListener = context as onButtonListener
            balagtasListener = context as onButtonListener
            baleteListener = context as onButtonListener
            lipaListener = context as onButtonListener
            bauanListener = context as onButtonListener
            bauanGtListener = context as onButtonListener
            lemeryListener = context as onButtonListener
            calambaLipaListener = context as onButtonListener
            calambaBalibagoListener = context as onButtonListener
            balibagoTagaytayListener = context as onButtonListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_jeep, container, false)

        val alangilanSwitch = view.findViewById<Button>(R.id.alangilanSwitch)
        alangilanSwitch.setOnClickListener{
            alangilanListener.alangilanButton()
        }

        val capitolioSwitch = view.findViewById<Button>(R.id.capitolioSwitch)
        capitolioSwitch.setOnClickListener {
            capitolioListener.capitolioButton()
        }

        val balagtasSwitch = view.findViewById<Button>(R.id.balagtasSwitch)
        balagtasSwitch.setOnClickListener {
            balagtasListener.balagtasButton()
        }

        val baleteSwitch = view.findViewById<Button>(R.id.baleteSwitch)
        baleteSwitch.setOnClickListener {
            baleteListener.baleteButton()
        }

        val lipaSwitch = view.findViewById<Button>(R.id.lipaSwitch)
        lipaSwitch.setOnClickListener {
            lipaListener.lipaButton()
        }

        val bauanSwitch = view.findViewById<Button>(R.id.bauanSwitch)
        bauanSwitch.setOnClickListener {
            bauanListener.bauanButton()
        }

        val bauanGtSwitch = view.findViewById<Button>(R.id.bauanGtSwitch)
        bauanGtSwitch.setOnClickListener {
            bauanGtListener.bauanGtButton()
        }

        val lemerySwitch = view.findViewById<Button>(R.id.lemerySwitch)
        lemerySwitch.setOnClickListener {
            lemeryListener.lemeryButton()
        }

        val calambaLipaSwitch = view.findViewById<Button>(R.id.calambaLipaSwitch)
        calambaLipaSwitch.setOnClickListener {
            calambaLipaListener.calambaLipaButton()
        }

        val calambaBalibagoSwitch = view.findViewById<Button>(R.id.calambaBalibagoSwitch)
        calambaBalibagoSwitch.setOnClickListener {
            calambaBalibagoListener.calambaBalibagoButton()
        }

        val balibagoTagaytaySwitch = view.findViewById<Button>(R.id.balibagoTagaytaySwitch)
        balibagoTagaytaySwitch.setOnClickListener {
            balibagoTagaytayListener.balibagoTagaytayButton()
        }

        // Inflate the layout for this fragment
        return view

    }

}