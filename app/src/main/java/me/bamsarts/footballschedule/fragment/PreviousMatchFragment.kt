package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.startActivity
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.activity.MatchDetailActivity
import me.bamsarts.footballschedule.model.Match
import kotlinx.android.synthetic.main.fragment_prev.*
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.adapter.PreviousMatchAdapter
import me.bamsarts.footballschedule.presenter.ListMatchPresenter
import me.bamsarts.footballschedule.view.ListMatchView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh

class PreviousMatchFragment : Fragment(), ListMatchView{

    private lateinit var spinner: Spinner
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var presenter: ListMatchPresenter
    private lateinit var adapter: PreviousMatchAdapter
    private var events = mutableListOf<Match>()
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_prev, container, false)
        swipe = view.find(R.id.swipe)
        swipe.setColorSchemeResources(R.color.colorTosca)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        swipe.isRefreshing = true

        spinner = spinner_sample

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, spinnerItems)


        spinner.adapter = spinnerAdapter


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


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    leagueName = spinner.selectedItem.toString()
                    when(leagueName){
                        "English Premier League" -> presenter.getPreviousMatch("4328")
                        "German Bundesliga" -> presenter.getPreviousMatch("4331")
                        "Italian Serie A" -> presenter.getPreviousMatch("4332")
                        "French Ligue 1" -> presenter.getPreviousMatch("4334")
                        "Spanish La Liga" -> presenter.getPreviousMatch("4335")
                        else -> presenter.getPreviousMatch("4328")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        swipe.onRefresh {
            when(leagueName){
                "English Premier League" -> presenter.getPreviousMatch("4328")
                "German Bundesliga" -> presenter.getPreviousMatch("4331")
                "Italian Serie A" -> presenter.getPreviousMatch("4332")
                "French Ligue 1" -> presenter.getPreviousMatch("4334")
                "Spanish La Liga" -> presenter.getPreviousMatch("4335")
                else -> presenter.getPreviousMatch("4328")
            }
        }

    }


    override fun showEventList(data: List<Match>) {
        swipe.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

}