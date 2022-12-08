package com.org.fordacommute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.findFragment
import org.w3c.dom.Text

open class DirectionsFragment : DialogFragment() {

    interface onButtonListener {
        fun startCurrentLocation()
        fun startMapLocation()
        fun destinationCurrentLocation()
        fun destinationMapLocation()
    }

    lateinit var startCurrentLocationListener: onButtonListener
    lateinit var startMapLocationListener: onButtonListener
    lateinit var destinationCurrentLocationListener: onButtonListener
    lateinit var destinationMapLocationListener: onButtonListener

    private val destinationCurrentLocation: ConstraintLayout? = view?.findViewById(R.id.destinationCurrentLocation)
    private val destinationMapLocation: ConstraintLayout? = view?.findViewById(R.id.destinationMapLocation)
    private val startText: Text? = view?.findViewById(R.id.startText)
    private val destinationText: Text? = view?.findViewById(R.id.destinationText)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startCurrentLocationListener = context as onButtonListener
        startMapLocationListener = context as onButtonListener
        destinationCurrentLocationListener = context as onButtonListener
        destinationMapLocationListener = context as onButtonListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_directions, container, false)

        val startCurrentLocation = view.findViewById<Button>(R.id.startCurrentLocation)
        startCurrentLocation.setOnClickListener {
            startCurrentLocationListener.startCurrentLocation()
        }

        val startMapLocation = view.findViewById<Button>(R.id.startMapLocation)
        startMapLocation.setOnClickListener {
            startMapLocationListener.startMapLocation()
        }

        val destinationCurrentLocation = view.findViewById<Button>(R.id.destinationCurrentLocation)
        destinationCurrentLocation.setOnClickListener {
            destinationCurrentLocationListener.destinationCurrentLocation()
        }

        val destinationMapLocation = view.findViewById<Button>(R.id.destinationMapLocation)
        destinationMapLocation.setOnClickListener {
            destinationMapLocationListener.destinationMapLocation()
        }

        return view
    }

}