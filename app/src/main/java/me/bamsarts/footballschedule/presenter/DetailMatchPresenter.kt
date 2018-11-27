package me.bamsarts.footballschedule.presenter

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.db.FavouriteData
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.db.database
import me.bamsarts.footballschedule.view.DetailView
import me.bamsarts.footballschedule.model.EventDetailResponse
import me.bamsarts.footballschedule.model.TeamResponse
import org.jetbrains.anko.db.*
import org.jetbrains.anko.longToast


class DetailMatchPresenter(private val context: Context,
                           private val view: DetailView,
                           private val apiRepository: ApiRepo,
                           private val gson: Gson) {

    fun getEventDetail(eventId: String) {

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDB.getEventDetail(eventId)).await(), EventDetailResponse::class.java)


            view.showDetailEvent(data.events)
        }

    }

    fun getTeamBadgeAway(idAway: String){

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDB.getTeamDetail(idAway)).await(),TeamResponse::class.java)

            view.showBadgeAway(data.teams[0].strTeamBadge)
        }

    }


    fun getTeamBadgeHome(idHome: String){

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDB.getTeamDetail(idHome)).await(), TeamResponse::class.java)

            view.showBadgeHome(data.teams[0].strTeamBadge)

        }

    }


    fun getFavoriteMatchById(idEvent: String?): MutableList<FavouriteData> {
        val events: MutableList<FavouriteData> = ArrayList()

        this.context.database.use {
            select(FavouriteData.TABLE_FAVOURITE_MATCH)
                .whereArgs("(ID_EVENT = {id_event})",
                    "id_event" to "${idEvent}").exec {
                    parseList(object : MapRowParser<MutableList<FavouriteData>> {
                        override fun parseRow(columns: Map<String, Any?>): MutableList<FavouriteData> {
                            val evt = FavouriteData(
                                columns["ID_EVENT"].toString(),
                                columns["strHomeTeam"].toString(),
                                columns["strAwayTeam"].toString(),
                                columns["intHomeScore"].toString(),
                                columns["intAwayScore"].toString(),
                                columns["DATE_EVENT"].toString(),
                                columns["strDate"].toString(),
                                columns["idHomeTeam"].toString(),
                                columns["idAwayTeam"].toString()
                            )
                            events.add(evt)

                            return events
                        }

                    })

                }
        }

        return events
    }

    fun saveFavoriteMatch(event: Match?) {
        this.context.database.use {
            insert(
                FavouriteData.TABLE_FAVOURITE_MATCH,
                FavouriteData.ID_EVENT to event?.eventId,
                FavouriteData.idHomeTeam to event?.homeId,
                FavouriteData.idAwayTeam to event?.awayId,
                FavouriteData.strHomeTeam to event?.awayTeam,
                FavouriteData.strAwayTeam to event?.eventDate,
                FavouriteData.intHomeScore to event?.homeScore,
                FavouriteData.intAwayScore to event?.awayScore,
                FavouriteData.DATE_EVENT to event?.homeTeam

            )
            context.longToast("Added To Favourite")
        }
    }

    fun deleteFavoriteMatch(idEvent: String?) {
        context.database.use {
            delete(FavouriteData.TABLE_FAVOURITE_MATCH, "(ID_EVENT = {id_event})", "id_event" to "${idEvent}")
            context.longToast("Removed From Favourite")

        }
    }
}