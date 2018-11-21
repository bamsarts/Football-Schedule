package me.bamsarts.footballschedule.presenter

import android.content.Context
import com.google.gson.Gson
import me.bamsarts.footballschedule.APIs.ApiRepo
import me.bamsarts.footballschedule.APIs.ApiRepository
import me.bamsarts.footballschedule.APIs.SportDB
import me.bamsarts.footballschedule.APIs.TheSportDB
import me.bamsarts.footballschedule.DB.Favorite
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.model.MatchesResponses
import me.bamsarts.footballschedule.DB.database
import me.bamsarts.footballschedule.view.DetailView
import me.bamsarts.footballschedule.model.EventDetailResponse
import org.jetbrains.anko.db.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetailPresenter(private val context: Context, private val view: DetailView, private val apiRepository: ApiRepo, private val gson: Gson) {

//    fun getClubDetailById(idEvent: String?) {
//        val service = ApiRepository.retrofit.create(SportDB::class.java).getEventDetailById(idEvent)
//        service.enqueue(object : Callback<MatchesResponses> {
//            override fun onFailure(call: Call<MatchesResponses>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<MatchesResponses>, response: Response<MatchesResponses>) {
//                response.body()?.events?.get(0)?.let { view.initValue(it) }
//            }
//
//        })
//    }

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


    fun getFavoriteMatchById(idEvent: String?): MutableList<Favorite> {
        val events: MutableList<Favorite> = ArrayList()

        this.context?.database?.use {
            select(Favorite.FavoriteMatch)
                .whereArgs("(idEvent = {id_event})",
                    "id_event" to "${idEvent}").exec {
                    parseList(object : MapRowParser<MutableList<Favorite>> {
                        override fun parseRow(columns: Map<String, Any?>): MutableList<Favorite> {
                            val evt = Favorite(
                                columns["idEvent"].toString(),
                                columns["idSoc                                                                                                                                                                                                                                                                          cer"].toString(),
                                columns["strHomeTeam"].toString(),
                                columns["strAwayTeam"].toString(),
                                columns["intHomeScore"].toString(),
                                columns["intAwayScore"].toString(),
                                columns["dateEvent"].toString(),
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
                Favorite.FavoriteMatch,
                Favorite.idEvent to event?.eventId,
                Favorite.idHomeTeam to event?.homeId,
                Favorite.idAwayTeam to event?.awayId,
                Favorite.strHomeTeam to event?.homeTeam,
                Favorite.strAwayTeam to event?.awayTeam,
                Favorite.intHomeScore to event?.homeScore,
                Favorite.intAwayScore to event?.awayScore,
                Favorite.dateEvent to event?.eventDate

            )
            context?.longToast("Match ditambahkan ke Favorit ${event?.eventId}")
        }
    }

    fun deleteFavoriteMatch(idEvent: String?) {
        context?.database?.use {
            delete(Favorite.FavoriteMatch, "(idEvent = {id_event})", "id_event" to "${idEvent}")
            context.longToast("Match ${idEvent} dihapus dari favorit")

        }
    }
}