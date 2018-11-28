package me.bamsarts.footballschedule.presenter

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.TeamResponse
import me.bamsarts.footballschedule.view.TeamsView
import okhttp3.Dispatcher
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepo,
                     private val gson: Gson) {

    fun getTeamList(league: String?) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getTeams(league)).await(),
                TeamResponse::class.java
            )


            view.showTeamList(data.teams)

        }

    }

    fun getTeamByName(teamName: String?) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getTeamByName(teamName)).await(),
                TeamResponse::class.java
            )

            view.showTeamList(data.teams)

        }

    }
}