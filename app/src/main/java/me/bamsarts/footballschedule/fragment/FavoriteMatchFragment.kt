package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.os.Handler
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
import me.bamsarts.footballschedule.DB.Favorite
import me.bamsarts.footballschedule.R.id.fav_recycler
import me.bamsarts.footballschedule.R.id.fav_swipe
import me.bamsarts.footballschedule.adapter.FavoriteRecyclerViewAdapter
import me.bamsarts.footballschedule.presenter.FavoriteMatchPresenter


class FavoriteMatchFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var presenter: FavoriteMatchPresenter
    private var favorites = mutableListOf<Favorite>()
    private lateinit var adapter: FavoriteRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteRecyclerViewAdapter(favorites) {
            startActivity<MatchDetailActivity>(
                "EVENT_ID" to "${it.idEvent}",
                "HOME_ID" to "${it.idHomeTeam}",
                "AWAY_ID" to "${it.idAwayTeam}"
            )
        }
        fav_recycler.layoutManager = LinearLayoutManager(this.context)
        fav_recycler.adapter = adapter

        initPresenter()

        fav_swipe.setOnRefreshListener(this)
    }


    private fun initPresenter() {
        presenter = FavoriteMatchPresenter(this.context)
        presenter.fetchFavMatches(favorites)
    }


    override fun onRefresh() {
        if (fav_swipe.isRefreshing) {
            Handler().postDelayed({
                fav_swipe.isRefreshing = false
                favorites.clear()
                presenter.fetchFavMatches(favorites)
                adapter.notifyDataSetChanged()
            }, 500)
        }
    }

}