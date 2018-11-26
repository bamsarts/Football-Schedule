package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.startActivity
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.MatchDetailActivity
import me.bamsarts.footballschedule.model.Match
import kotlinx.android.synthetic.main.fragment_prev.*
import me.bamsarts.footballschedule.APIs.ApiRepo
import me.bamsarts.footballschedule.R.id.idPreviousMatch
import me.bamsarts.footballschedule.adapter.PreviousMatchAdapter
import me.bamsarts.footballschedule.presenter.ListMatchPresenter
import me.bamsarts.footballschedule.view.ListMatchView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh

class PreviousMatchFragment : Fragment(), ListMatchView{

    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var presenter: ListMatchPresenter
    private lateinit var adapter: PreviousMatchAdapter
    private var events = mutableListOf<Match>()
    private val leagueId = 4396

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_prev, container, false)
        swipe = view.find(R.id.swipe)
        swipe.setColorSchemeResources(R.color.colorTosca)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        swipe.isRefreshing = true

        adapter = PreviousMatchAdapter(events) {
            startActivity<MatchDetailActivity>(
                "EVENT_ID" to it.eventId,
                "HOME_ID" to it.homeId,
                "AWAY_ID" to it.awayId
            )
        }

        idPreviousMatch.layoutManager = LinearLayoutManager(context)
        idPreviousMatch.adapter = adapter
        presenter = ListMatchPresenter(this, ApiRepo(), Gson())
        presenter.getPreviousMatch()
        swipe.onRefresh {
            presenter.getPreviousMatch()
        }
    }


    override fun showEventList(data: List<Match>) {
        swipe.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

}