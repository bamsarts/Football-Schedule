package me.bamsarts.footballschedule.presenter

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.PlayerResponse
import me.bamsarts.footballschedule.view.PlayersView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class PlayersPresenter(private val view: PlayersView,
                       private val apiRepository: ApiRepo,
                       private val gson: Gson) {

    fun getPlayerList(teamId: String?) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getAllPlayer(teamId)).await(),
                PlayerResponse::class.java
            )


            view.showPlayerList(data.player)

        }

    }
}