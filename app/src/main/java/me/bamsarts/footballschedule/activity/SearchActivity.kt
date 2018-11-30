package me.bamsarts.footballschedule.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.adapter.SearchMatchAdapter
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.presenter.DetailMatchPresenter
import me.bamsarts.footballschedule.presenter.SearchPresenter
import me.bamsarts.footballschedule.view.SearchMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchActivity : AppCompatActivity(), SearchMatchView{

    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var events: RecyclerView
    private lateinit var adapter: SearchMatchAdapter
    private var matchs: MutableList<Match> = mutableListOf()
    private lateinit var nameTeam: String
    private val presenter: SearchPresenter = SearchPresenter(this, ApiRepo(), Gson())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nameTeam = intent.getStringExtra("matchName")

        swipe = swipeRefreshLayout {

            setColorSchemeResources(
                R.color.colorTosca,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

            events = recyclerView {
                lparams (width = matchParent, height = wrapContent)
                layoutManager = LinearLayoutManager(this@SearchActivity)
            }
        }

        adapter = SearchMatchAdapter(matchs){
            startActivity<MatchDetailActivity>(
                "EVENT_ID" to "${it.eventId}",
                "HOME_ID" to "${it.homeId}",
                "AWAY_ID" to "${it.awayId}")
        }

        events.adapter = adapter

        presenter.getMatchSearch(nameTeam)

        swipe.onRefresh {
            presenter.getMatchSearch(nameTeam)
        }

    }

    override fun showMatchList(data: List<Match>) {
        swipe.isRefreshing = false
        matchs.clear()
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView = menu?.findItem(R.id.idSearch)?.actionView as SearchView?
        searchView?.queryHint = "Search Match..."

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(matchName: String?): Boolean {
                presenter.getMatchSearch(matchName)
                return false
            }
        })
        return true
    }
}
