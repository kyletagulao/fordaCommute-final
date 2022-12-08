package com.org.fordacommute

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class TerminalsFragment : Fragment() {

    interface onTerminalButtonListener {
        fun jeepneyButton()
        fun busButton()
        fun vanButton()
    }

    lateinit var jeepneyListener: onTerminalButtonListener
    lateinit var busListener: onTerminalButtonListener
    lateinit var vanListener: onTerminalButtonListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        jeepneyListener = context as onTerminalButtonListener
        busListener = context as onTerminalButtonListener
        vanListener = context as onTerminalButtonListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_terminals, container, false)

        val jeepneySwitch = view.findViewById<Button>(R.id.jeepneySwitch)
        jeepneySwitch.setOnClickListener{
            jeepneyListener.jeepneyButton()
        }

        val busSwitch = view.findViewById<Button>(R.id.busSwitch)
        busSwitch.setOnClickListener {
            busListener.busButton()
        }

        val vanSwitch = view.findViewById<Button>(R.id.vanSwitch)
        vanSwitch.setOnClickListener {
            vanListener.vanButton()
        }

        // Inflate the layout for this fragment
        return view
    }

}