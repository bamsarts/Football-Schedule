package me.bamsarts.footballschedule.presenter

import com.google.gson.Gson
import me.bamsarts.footballschedule.APIs.ApiRepo
import me.bamsarts.footballschedule.APIs.TheSportDB
import me.bamsarts.footballschedule.model.MatchResponse
import me.bamsarts.footballschedule.view.ListMatchView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ListMatchPresenter(private val view: ListMatchView, private val apiRepository: ApiRepo, private val gson: Gson) {

    fun getPreviousMatch() {
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getPreviousMatch()), MatchResponse::class.java
            )

            uiThread {
                view.showEventList(data.events)
            }
        }
    }

    fun getNextMatch(){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDB.getNextMatch()), MatchResponse::class.java)

            uiThread {
                view.showEventList(data.events)
            }
        }
    }

}