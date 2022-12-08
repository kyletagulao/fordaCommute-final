package com.org.fordacommute

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class RouteViewAdapter(var context: Context, fm: FragmentManager, var totalTabs: Int) : FragmentPagerAdapter(fm) {



    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                BusFragment()
            }
            1 -> {
                JeepFragment()
            }

            2 -> {
                TerminalsFragment()
            }
            else -> getItem(position)
        }
    }

}