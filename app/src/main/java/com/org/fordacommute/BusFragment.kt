package com.org.fordacommute

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.findFragment

open class BusFragment : Fragment() {

    interface onButtonListener {
        fun nasugbuButton()
        fun alpsPitxButton()
        fun bsePitxButton()
        fun ceresPitxButton()
        fun dltbPitxButton()
        fun goldstarPitxButton()
        fun jamPitxButton()
        fun delarosaPitxButton()
        fun rrcgPitxButton()
        fun japsButton()
        fun supremeLucenaButton()
    }

    lateinit var nasugbuListener: onButtonListener
    lateinit var alpsPitxListener: onButtonListener
    lateinit var bsePitxListener: onButtonListener
    lateinit var ceresPitxListener: onButtonListener
    lateinit var dltbPitxListener: onButtonListener
    lateinit var goldstarPitxListener: onButtonListener
    lateinit var jamPitxListener: onButtonListener
    lateinit var delarosaPitxListener: onButtonListener
    lateinit var rrcgPitxListener: onButtonListener
    lateinit var japsListener: onButtonListener
    lateinit var supremeLucenaListener: onButtonListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
            nasugbuListener = context as onButtonListener
            alpsPitxListener = context as onButtonListener
            bsePitxListener = context as onButtonListener
            ceresPitxListener = context as onButtonListener
            dltbPitxListener = context as onButtonListener
            goldstarPitxListener = context as onButtonListener
            jamPitxListener = context as onButtonListener
            delarosaPitxListener = context as onButtonListener
            rrcgPitxListener = context as onButtonListener
            japsListener = context as onButtonListener
            supremeLucenaListener = context as onButtonListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bus, container, false)

        val nasugbuSwitch = view.findViewById<Button>(R.id.nasugbuSwitch)
        nasugbuSwitch.setOnClickListener {
            nasugbuListener.nasugbuButton()
        }

        val alpsPitxSwitch = view.findViewById<Button>(R.id.alpsPitxSwitch)
        alpsPitxSwitch.setOnClickListener {
            alpsPitxListener.alpsPitxButton()
        }

        val bsePitxSwitch = view.findViewById<Button>(R.id.bsePitxSwitch)
        bsePitxSwitch.setOnClickListener {
            bsePitxListener.bsePitxButton()
        }

        val ceresPitxSwitch = view.findViewById<Button>(R.id.ceresPitxSwitch)
        ceresPitxSwitch.setOnClickListener {
            ceresPitxListener.ceresPitxButton()
        }

        val dltbPitxSwitch = view.findViewById<Button>(R.id.dltbPitxSwitch)
        dltbPitxSwitch.setOnClickListener {
            dltbPitxListener.dltbPitxButton()
        }

        val goldstarPitxSwitch = view.findViewById<Button>(R.id.goldstarPitxSwitch)
        goldstarPitxSwitch.setOnClickListener {
            goldstarPitxListener.goldstarPitxButton()
        }

        val jamPitxSwitch = view.findViewById<Button>(R.id.jamPitxSwitch)
        jamPitxSwitch.setOnClickListener {
            jamPitxListener.jamPitxButton()
        }

        val delarosaPitxSwitch = view.findViewById<Button>(R.id.delarosaPitxSwitch)
        delarosaPitxSwitch.setOnClickListener {
            delarosaPitxListener.delarosaPitxButton()
        }

        val rrcgPitxSwitch = view.findViewById<Button>(R.id.rrcgPitxSwitch)
        rrcgPitxSwitch.setOnClickListener {
            rrcgPitxListener.rrcgPitxButton()
        }

        val japsSwitch = view.findViewById<Button>(R.id.japsSwitch)
        japsSwitch.setOnClickListener {
            japsListener.japsButton()
        }

        val supremeLucenaSwitch = view.findViewById<Button>(R.id.supremeLucenaSwitch)
        supremeLucenaSwitch.setOnClickListener {
            supremeLucenaListener.supremeLucenaButton()
        }

        return view
    }

}