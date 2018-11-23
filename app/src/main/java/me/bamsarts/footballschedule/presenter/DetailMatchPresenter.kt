package me.bamsarts.footballschedule.presenter

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import me.bamsarts.footballschedule.APIs.ApiRepo
import me.bamsarts.footballschedule.APIs.ApiRepository
import me.bamsarts.footballschedule.APIs.SportDB
import me.bamsarts.footballschedule.APIs.TheSportDB
import me.bamsarts.footballschedule.DB.FavouriteData
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.DB.database
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.R.id.imgAway
import me.bamsarts.footballschedule.activity.MatchDetailActivity
import me.bamsarts.footballschedule.view.DetailView
import me.bamsarts.footballschedule.model.EventDetailResponse
import me.bamsarts.footballschedule.model.TeamResponses
import org.jetbrains.anko.db.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMatchPresenter(private val context: Context, private val view: DetailView, private val apiRepository: ApiRepo, private val gson: Gson) {

    private lateinit var MatchDetailActivity: MatchDetailActivity

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
            ApiRepository.retrofit.create(SportDB::class.java)
                .getClubDetailById(idAway)
                .enqueue(object : Callback<TeamResponses> {
                    override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                        val imgUrl = response.body()?.teams?.get(0)?.strTeamBadge

                        uiThread {
                            view.showBadgeAway(imgUrl)
                        }
                    }
                })
        }

    }

    fun getTeamBadgeHome(idHome: String){
        doAsync {
            ApiRepository.retrofit
                .create(SportDB::class.java)
                .getClubDetailById(idHome)
                .enqueue(object : Callback<TeamResponses> {
                    override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                        val imgUrl = response.body()?.teams?.get(0)?.strTeamBadge

                        uiThread {
                            view.showBadgeHome(imgUrl)
                        }

                    }

                })
        }
    }


    fun getFavoriteMatchById(idEvent: String?): MutableList<FavouriteData> {
        val events: MutableList<FavouriteData> = ArrayList()

        this.context?.database?.use {
            select(FavouriteData.FavoriteMatch)
                .whereArgs("(idEvent = {id_event})",
                    "id_event" to "${idEvent}").exec {
                    parseList(object : MapRowParser<MutableList<FavouriteData>> {
                        override fun parseRow(columns: Map<String, Any?>): MutableList<FavouriteData> {
                            val evt = FavouriteData(
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
                FavouriteData.FavoriteMatch,
                FavouriteData.idEvent to event?.eventId,
                FavouriteData.idHomeTeam to event?.homeId,
                FavouriteData.idAwayTeam to event?.awayId,
                FavouriteData.strHomeTeam to event?.homeTeam,
                FavouriteData.strAwayTeam to event?.awayTeam,
                FavouriteData.intHomeScore to event?.homeScore,
                FavouriteData.intAwayScore to event?.awayScore,
                FavouriteData.dateEvent to event?.eventDate

            )
            context?.longToast("Match ditambahkan ke Favorit ${event?.eventId}")
        }
    }

    fun deleteFavoriteMatch(idEvent: String?) {
        context?.database?.use {
            delete(FavouriteData.FavoriteMatch, "(idEvent = {id_event})", "id_event" to "${idEvent}")
            context.longToast("Match ${idEvent} dihapus dari favorit")

        }
    }
}