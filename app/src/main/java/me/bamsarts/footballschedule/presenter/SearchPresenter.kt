package me.bamsarts.footballschedule.presenter

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.SearchMatchResponse
import me.bamsarts.footballschedule.view.SearchMatchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class SearchPresenter(private val view: SearchMatchView,
                      private val apiRepository: ApiRepo,
                      private val gson: Gson) {

    fun getMatchSearch(teamName: String?) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getMatchSearch(teamName)).await(),
                SearchMatchResponse::class.java
            )


            view.showMatchList(data.event)

        }
    }
}