package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import me.bamsarts.footballschedule.adapter.TeamsAdapter
import me.bamsarts.footballschedule.model.Team
import me.bamsarts.footballschedule.presenter.TeamsPresenter
import me.bamsarts.footballschedule.view.TeamsView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import me.bamsarts.footballschedule.R.array.league
import me.bamsarts.footballschedule.activity.TeamDetailActivity
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.R
import kotlinx.android.synthetic.main.fragment_club.*

class TeamsFragment : Fragment(), TeamsView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var spinner: Spinner
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe.isRefreshing = true

        setHasOptionsMenu(true)
        spinner = spinnerClub

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = TeamsAdapter(teams) {
            context?.startActivity<TeamDetailActivity>("id" to "${it.idTeam}", "teamName" to "${it.strTeam}")
        }

        idRecycleClubs.layoutManager = LinearLayoutManager(context)
        idRecycleClubs.adapter = adapter

        val request = ApiRepo()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                swipe.isRefreshing = true
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipe.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_club, container, false)
        swipe = view.find(R.id.swipeClub)
        swipe.setColorSchemeResources(R.color.colorTosca)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search, menu)
        val searchView = menu?.findItem(R.id.idSearch)?.actionView as SearchView?
        searchView?.queryHint = "Search teams.."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.getTeamByName(newText)
                return false
            }
        })

        searchView?.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                presenter.getTeamList(leagueName)
                return true
            }
        })
    }


    override fun showTeamList(data: List<Team>) {
        swipe.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

}