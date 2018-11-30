package me.bamsarts.footballschedule.presenter

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.PlayerResponse
import me.bamsarts.footballschedule.view.DetailPlayerView
import me.bamsarts.footballschedule.view.PlayersView

class DetailPlayerPresenter(private val view: DetailPlayerView,
                            private val apiRepository: ApiRepo,
                            private val gson: Gson) {

    fun getPlayer(playerId: String?) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getPlayerById(playerId)).await(),
                PlayerResponse::class.java
            )


            view.showPlayer(data.player)

        }

    }
}