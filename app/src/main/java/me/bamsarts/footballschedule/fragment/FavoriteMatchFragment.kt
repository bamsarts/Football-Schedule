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
import me.bamsarts.footballschedule.detail.MatchDetailActivity
import me.bamsarts.footballschedule.model.LocalEvent
import org.jetbrains.anko.support.v4.startActivity
import kotlinx.android.synthetic.main.favorite_match_fragment.*


class FavoriteMatchFragment : Fragment(), FavoriteMatchContract.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var presenter: FavoriteMatchPresenter
    private var listEvent = mutableListOf<LocalEvent>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorite_match_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()

        val adapter = FavoriteRecyclerViewAdapter(listEvent) {
            startActivity<MatchDetailActivity>(
                "EVENT_ID" to it.idEvent,
                "HOME_ID" to it.idHomeTeam,
                "AWAY_ID" to it.idAwayTeam
            )
        }
        fav_recycler.layoutManager = LinearLayoutManager(this.context)
        fav_recycler.adapter = adapter

        fav_swipe.setOnRefreshListener(this)
    }

    private fun initPresenter() {
        presenter = FavoriteMatchPresenter(this.context, this)
        presenter.fetchFavMatches(listEvent)
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            fav_progress.visibility = View.VISIBLE
        } else {
            fav_progress.visibility = View.GONE
        }
    }

    override fun onRefresh() {
        if (fav_swipe.isRefreshing) {
            Handler().postDelayed({
                listEvent.clear()
                presenter.fetchFavMatches(listEvent)
                fav_swipe.isRefreshing = false
            }, 500)
        }
    }

}