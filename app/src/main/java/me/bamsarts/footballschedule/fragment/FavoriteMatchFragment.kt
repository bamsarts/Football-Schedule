package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.MatchDetailActivity
import org.jetbrains.anko.support.v4.startActivity
import kotlinx.android.synthetic.main.fragment_favorite.*
import me.bamsarts.footballschedule.DB.FavouriteData
import me.bamsarts.footballschedule.R.id.fav_recycler
import me.bamsarts.footballschedule.adapter.FavouriteAdapter
import me.bamsarts.footballschedule.presenter.FavoriteMatchPresenter
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh


class FavoriteMatchFragment : Fragment() {

    private var favourites = mutableListOf<FavouriteData>()
    private lateinit var adapter: FavouriteAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var presenter: FavoriteMatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        swipe = rootView.find(R.id.fav_swipe)
        swipe.setColorSchemeResources(R.color.colorTosca)

        adapter = FavouriteAdapter(favourites) {
            startActivity<MatchDetailActivity>(
                "EVENT_ID" to "${it.idEvent}",
                "HOME_ID" to "${it.idHomeTeam}",
                "AWAY_ID" to "${it.idAwayTeam}"
            )
        }

        swipe.onRefresh {
            getFavorites()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fav_recycler.layoutManager = LinearLayoutManager(this.context)
        fav_recycler.adapter = adapter

        initPresenter()

    }

    private fun initPresenter() {
        presenter = FavoriteMatchPresenter(this.context)
        presenter.fetchFavMatches(favourites)
    }

    private fun getFavorites(){
        swipe.isRefreshing = false
        favourites.clear()
        presenter.fetchFavMatches(favourites)
        adapter.notifyDataSetChanged()
    }


}