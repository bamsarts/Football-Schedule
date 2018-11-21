package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.bamsarts.footballschedule.APIs.ApiRepository
import me.bamsarts.footballschedule.APIs.SportDB
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.model.MatchesResponses
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.MatchDetailActivity
import kotlinx.android.synthetic.main.fragment_next.*
import me.bamsarts.footballschedule.adapter.NextRecyclerViewAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh


class NextFragment : Fragment() {

    private var service : Call<MatchesResponses>? = null
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_next, container, false)
        swipe = view.find(R.id.swipe)
        swipe.setColorSchemeResources(R.color.colorTosca)
        swipe.onRefresh {
            swipe.isRefreshing = false
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe.isRefreshing = true

        service = ApiRepository.retrofit.create(SportDB::class.java).getNextMatch()
        service?.enqueue(object : Callback<MatchesResponses> {
            override fun onFailure(call: Call<MatchesResponses>, t: Throwable) {
                Log.e("ERROR_RESPONSE", t.localizedMessage)
            }

            override fun onResponse(call: Call<MatchesResponses>, response: Response<MatchesResponses>) {
                swipe.isRefreshing = false
                response.body()?.events?.let {
                    initRecyclerView(it)
                }
            }

        })
    }

    private fun initRecyclerView(list: MutableList<Match>) {
        rv_next.layoutManager = LinearLayoutManager(context)
        val adapter = NextRecyclerViewAdapter(list) {
            startActivity<MatchDetailActivity>(
                "EVENT_ID" to it.eventId,
                "HOME_ID" to it.homeId,
                "AWAY_ID" to it.awayId
            )
        }
        rv_next.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (service?.isExecuted == true){
            service?.cancel()
        }
    }
}