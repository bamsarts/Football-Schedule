package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_club.*
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.TeamDetailActivity
import me.bamsarts.footballschedule.adapter.FavouriteTeamAdapter
import me.bamsarts.footballschedule.db.FavouriteTeam
import me.bamsarts.footballschedule.presenter.FavoriteMatchPresenter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteTeamsFragment: Fragment() {
    private var favorites: MutableList<FavouriteTeam> = mutableListOf()
    private lateinit var adapter: FavouriteTeamAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var presenter: FavoriteMatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_club, container, false)

        adapter = FavouriteTeamAdapter(favorites){
            context?.startActivity<TeamDetailActivity>("id" to "${it.teamId}", "teamName" to "${it.teamName}")
        }

        swipe = view.find(R.id.swipeClub)
        swipe.setColorSchemeResources(R.color.colorTosca)

        swipe.onRefresh {
            favorites.clear()
            getFavorites()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idRecycleClubs.layoutManager = LinearLayoutManager(context)
        idRecycleClubs.adapter = adapter

        initPresenter()
    }


    private fun initPresenter() {
        presenter = FavoriteMatchPresenter(this.context)
        presenter.fetchFavTeams(favorites)
    }

    private fun getFavorites(){
        swipe.isRefreshing = false
        favorites.clear()
        presenter.fetchFavTeams(favorites)
        adapter.notifyDataSetChanged()
    }

}
