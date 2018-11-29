package me.bamsarts.footballschedule.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.adapter.SearchMatchAdapter
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.presenter.SearchPresenter
import me.bamsarts.footballschedule.view.SearchMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchActivity : AppCompatActivity(), SearchMatchView{

    private lateinit var listEvent: RecyclerView
//    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout


    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: SearchPresenter
    private lateinit var adapter: SearchMatchAdapter

    private lateinit var teamName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        teamName = intent.getStringExtra("matchName")

        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)


            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        //id = R.id.listEvent
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(this@SearchActivity)
                    }

//                    progressBar = progressBar {
//                    }.lparams{
//                        centerHorizontally()
//                    }
                }
            }
        }

        adapter = SearchMatchAdapter(matches){
            ctx.startActivity<MatchDetailActivity>(
                "EVENT_ID" to "${it.eventId}",
                "HOME_ID" to "${it.homeId}",
                "AWAY_ID" to "${it.awayId}")
        }
        listEvent.adapter = adapter

        val request = ApiRepo()
        val gson = Gson()
        presenter = SearchPresenter(this, request, gson)
        presenter.getMatchSearch(teamName)

        swipeRefresh.onRefresh {
            presenter.getMatchSearch(teamName)
        }


    }

//    override fun showLoading() {
//        progressBar.visible()
//    }
//
//    override fun hideLoading() {
//        progressBar.invisible()
//    }

    override fun showMatchList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
        searchView?.queryHint = "Search Matches"

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
