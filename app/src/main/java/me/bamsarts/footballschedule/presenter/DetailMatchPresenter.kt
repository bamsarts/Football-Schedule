package me.bamsarts.footballschedule.presenter

import android.content.Context
import com.google.gson.Gson
import me.bamsarts.footballschedule.APIs.ApiRepo
import me.bamsarts.footballschedule.APIs.TheSportDB
import me.bamsarts.footballschedule.DB.FavouriteData
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.DB.database
import me.bamsarts.footballschedule.view.DetailView
import me.bamsarts.footballschedule.model.EventDetailResponse
import me.bamsarts.footballschedule.model.TeamResponse
import org.jetbrains.anko.db.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(private val context: Context, private val view: DetailView, private val apiRepository: ApiRepo, private val gson: Gson) {

    fun getEventDetail(eventId: String) {
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDB.getEventDetail(eventId)),
                EventDetailResponse::class.java
            )

            uiThread {
                view.showDetailEvent(data.events.get(0))
            }
        }
    }

    fun getTeamBadgeAway(idAway: String){

        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDB.getTeamDetail(idAway)),TeamResponse::class.java)

            uiThread {
                view.showBadgeAway(data.teams?.get(0)?.strTeamBadge)
            }

        }
    }


    fun getTeamBadgeHome(idHome: String){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDB.getTeamDetail(idHome)), TeamResponse::class.java)
            uiThread {
                view.showBadgeHome(data.teams?.get(0)?.strTeamBadge)
            }
        }
    }


    fun getFavoriteMatchById(idEvent: String?): MutableList<FavouriteData> {
        val events: MutableList<FavouriteData> = ArrayList()

        this.context?.database?.use {
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
        this.context?.database?.use {
            insert(
                FavouriteData.TABLE_FAVOURITE_MATCH,
                FavouriteData.ID_EVENT to event?.eventId,
                FavouriteData.idHomeTeam to event?.homeId,
                FavouriteData.idAwayTeam to event?.awayId,
                FavouriteData.strHomeTeam to event?.homeTeam,
                FavouriteData.strAwayTeam to event?.awayTeam,
                FavouriteData.intHomeScore to event?.homeScore,
                FavouriteData.intAwayScore to event?.awayScore,
                FavouriteData.DATE_EVENT to event?.eventDate

            )
            context?.longToast("Added To Favourite")
        }
    }

    fun deleteFavoriteMatch(idEvent: String?) {
        context?.database?.use {
            delete(FavouriteData.TABLE_FAVOURITE_MATCH, "(ID_EVENT = {id_event})", "id_event" to "${idEvent}")
            context.longToast("Removed From Favourite")

        }
    }
}