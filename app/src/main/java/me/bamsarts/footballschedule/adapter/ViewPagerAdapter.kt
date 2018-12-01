package me.bamsarts.footballschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager?) : FragmentPagerAdapter(fragmentManager){

    var fragmentList = arrayListOf<Fragment>()
    var titleList = arrayListOf<String>()

    override fun getItem(position: Int) = fragmentList[position]

    fun populateFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int) = titleList[position]

    override fun getCount() = fragmentList.size
}