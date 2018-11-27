package me.bamsarts.footballschedule.presenter

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.MatchResponse
import me.bamsarts.footballschedule.view.ListMatchView

class ListMatchPresenter(private val view: ListMatchView, private val apiRepository: ApiRepo, private val gson: Gson) {

    fun getPreviousMatch(matchId: String?) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDB.getPreviousMatch(matchId)).await(), MatchResponse::class.java
                )

            view.showEventList(data.events)
        }

    }

    fun getNextMatch(){

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDB.getNextMatch()).await(), MatchResponse::class.java)

            view.showEventList(data.events)

        }

    }

}