package me.bamsarts.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.model.Team
import me.bamsarts.footballschedule.presenter.TeamOverviewPresenter
import me.bamsarts.footballschedule.view.TeamOverviewView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import kotlinx.android.synthetic.main.club_description.*

class TeamOverviewFragment : Fragment(), TeamOverviewView {

    private var presenter: TeamOverviewPresenter = TeamOverviewPresenter(this, ApiRepo(), Gson())
    private lateinit var teams: Team
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.club_description, container, false)

        swipe = view.find(R.id.swipeTeamOverview)
        swipe.setColorSchemeResources(R.color.colorTosca)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe.isRefreshing = true

        val teamid: String? = arguments?.getString("teamId")

        presenter.getTeamDetail(teamid)

        swipe.onRefresh {
            presenter.getTeamDetail(teamid)
        }
    }


    override fun showTeamDetail(data: List<Team>) {
        swipe.isRefreshing = false

        teams = Team(data[0].idTeam,
            data[0].strTeam,
            data[0].strTeamBadge)

        Glide.with(this).load(data[0].strTeamBadge).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(clubBadge)
        clubName.text = data[0].strTeam
        clubDescription.text = data[0].strDescriptionEN
        clubYear.text = data[0].intFormedYear
        clubStadium.text = data[0].strStadium
    }

}