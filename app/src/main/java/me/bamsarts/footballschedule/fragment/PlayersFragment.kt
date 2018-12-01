package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player.*
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.PlayerDetailActivity
import me.bamsarts.footballschedule.adapter.PlayersAdapter
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.model.Player
import me.bamsarts.footballschedule.presenter.PlayersPresenter
import me.bamsarts.footballschedule.view.PlayersView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh

class PlayersFragment : Fragment(), PlayersView {

    private var player: MutableList<Player> = mutableListOf()
    private var presenter: PlayersPresenter = PlayersPresenter(this, ApiRepo(), Gson())
    private lateinit var adapter: PlayersAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var teamId: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe.isRefreshing = true

        teamId = arguments?.getString("teamId").toString()

        adapter = PlayersAdapter(player) {
            context?.startActivity<PlayerDetailActivity>(
                "playerName" to "${it.strPlayer}",
                "playerImage" to "${it.strFanart1}",
                "playerDesc" to "${it.strDescriptionEN}",
                "playerHeight" to "${it.strHeight}",
                "playerWeight" to "${it.strWeight},",
                "playerPosition" to "${it.strPosition}")
        }

        idRecyclePlayer.layoutManager = LinearLayoutManager(context)
        idRecyclePlayer.adapter = adapter

        presenter.getPlayerList(teamId)

        swipe.onRefresh {
            presenter.getPlayerList(teamId)
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_player, container, false)
        swipe = view.find(R.id.swipePlayers)
        swipe.setColorSchemeResources(R.color.colorTosca)

        return view

    }

    override fun showPlayerList(data: List<Player>) {
        swipe.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }

}