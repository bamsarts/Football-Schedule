package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.SearchActivity
import me.bamsarts.footballschedule.adapter.ViewPagerAdapter
import me.bamsarts.footballschedule.presenter.SearchPresenter
import org.jetbrains.anko.startActivity

class MatchFragment : Fragment() {

    private lateinit var presenter: SearchPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vPager = view.findViewById<ViewPager>(R.id.match_viewpager)
        val tabs = view.findViewById<TabLayout>(R.id.match_tabs)
        setHasOptionsMenu(true)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.populateFragment(PreviousMatchFragment(), "Previous Match")
        adapter.populateFragment(NextMatchFragment(), "Next Match")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search, menu)

        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?

        searchView?.queryHint = "Search match..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(matchName: String): Boolean {
                context?.startActivity<SearchActivity>("matchName" to matchName)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                presenter.getMatchSearch(newText)
                return false
            }
        })

//        searchView?.setOnCloseListener(object: SearchView.OnCloseListener{
//            override fun onClose(): Boolean {
//                presenter.getTeamList(leagueName)
//                return true
//            }
//        })
    }
}