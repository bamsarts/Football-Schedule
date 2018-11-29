package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.adapter.ViewPagerAdapter

class FavouriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favourite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vPager = view.findViewById<ViewPager>(R.id.fav_viewpager)
        val tabs = view.findViewById<TabLayout>(R.id.fav_tabs)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.populateFragment(FavoriteMatchFragment(), "Favorite Match")
        adapter.populateFragment(FavoriteTeamsFragment(), "Favorite Team")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }


}