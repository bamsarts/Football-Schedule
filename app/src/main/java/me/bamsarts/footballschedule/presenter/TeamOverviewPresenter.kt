package me.bamsarts.footballschedule.presenter

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.TeamResponse
import me.bamsarts.footballschedule.view.TeamOverviewView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class TeamOverviewPresenter(private val view: TeamOverviewView,
                            private val apiRepository: ApiRepo,
                            private val gson: Gson) {

    fun getTeamDetail(teamId: String?) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getTeamDetail(teamId)).await(),
                TeamResponse::class.java
            )


            view.showTeamDetail(data.teams)

        }

    }
}