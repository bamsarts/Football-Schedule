package me.bamsarts.footballschedule.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.R.attr.colorAccent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.model.Team
import me.bamsarts.footballschedule.presenter.TeamOverviewPresenter
import me.bamsarts.footballschedule.view.TeamOverviewView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamOverviewFragment : Fragment(), AnkoComponent<Context>, TeamOverviewView {

    override fun showTeamDetail(data: List<Team>) {
        teams = Team(data[0].idTeam,
            data[0].strTeam,
            data[0].strTeamBadge)
        swipeRefresh.isRefreshing = false
//        Picasso.get().load(data[0].strTeamBadge).into(teamBadge)
        Glide.with(this).load(data[0].strTeamBadge).into(teamBadge)
        teamName.text = data[0].strTeam
        teamDescription.text = data[0].strDescriptionEN
        teamFormedYear.text = data[0].intFormedYear
        teamStadium.text = data[0].strStadium
    }

    private lateinit var presenter: TeamOverviewPresenter
    private lateinit var teams: Team
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val teamid: String? = arguments?.getString("teamId")
        Log.d("myfragment on activity", teamid)

        val request = ApiRepo()
        val gson = Gson()
        presenter = TeamOverviewPresenter(this, request, gson)
        presenter.getTeamDetail(teamid)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(teamid)
        }
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout{
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamBadge =  imageView {}.lparams(height = dip(75))

                            teamName = textView{
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, colorAccent)
                            }.lparams{
                                topMargin = dip(5)
                            }

                            teamFormedYear = textView{
                                this.gravity = Gravity.CENTER
                            }

                            teamStadium = textView{
                                this.gravity = Gravity.CENTER

                            }

                            teamDescription = textView().lparams{
                                topMargin = dip(20)
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

    }

}