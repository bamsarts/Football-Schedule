package me.bamsarts.footballschedule.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.PlayerDetailActivity
import me.bamsarts.footballschedule.adapter.PlayersAdapter
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.model.Player
import me.bamsarts.footballschedule.presenter.PlayersPresenter
import me.bamsarts.footballschedule.view.PlayersView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayersFragment : Fragment(), AnkoComponent<Context>, PlayersView {

    private var player: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayersPresenter
    private lateinit var adapter: PlayersAdapter
    private lateinit var listPlayer: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var teamId: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        teamId = arguments?.getString("teamId").toString()
        Log.d("teamid player", teamId)

        adapter = PlayersAdapter(player) {
            context?.startActivity<PlayerDetailActivity>(
                "playerName" to "${it.strPlayer}",
                "playerImage" to "${it.strFanart1}",
                "playerDesc" to "${it.strDescriptionEN}",
                "playerHeight" to "${it.strHeight}",
                "playerWeight" to "${it.strWeight},",
                "playerPosition" to "${it.strPosition}")
        }
        listPlayer.adapter = adapter

        val request = ApiRepo()
        val gson = Gson()
        presenter = PlayersPresenter(this, request, gson)
        presenter.getPlayerList(teamId)

        swipeRefresh.onRefresh {
            presenter.getPlayerList(teamId)
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
                        id = R.id.listPlayer
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

//                    progressBar = progressBar {
//                    }.lparams{
//                        centerHorizontally()
//                    }
                }
            }
        }
    }

    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }

}