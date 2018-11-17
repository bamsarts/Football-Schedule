package me.bamsarts.footballschedule.detail

import android.content.Context
import kotlinx.coroutines.experimental.selects.select
import me.bamsarts.footballschedule.api.ApiRepository
import me.bamsarts.footballschedule.api.TheSportDBApi
import me.bamsarts.footballschedule.model.LocalEvent
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.model.MatchesResponses
import me.bamsarts.footballschedule.utils.database
import org.jetbrains.anko.db.*
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetailPresenter(private val context: Context?, private val view: DetailView) {
    fun getClubDetailById(idEvent: String?) {
        val service = ApiRepository.retrofit.create(TheSportDBApi::class.java).getEventDetailById(idEvent)
        service.enqueue(object : Callback<MatchesResponses> {
            override fun onFailure(call: Call<MatchesResponses>, t: Throwable) {

            }

            override fun onResponse(call: Call<MatchesResponses>, response: Response<MatchesResponses>) {
                response.body()?.events?.get(0)?.let { view.initValue(it) }
            }

        })
    }

    fun getFavoriteMatchById(idEvent: String?): MutableList<LocalEvent> {
        val events: MutableList<LocalEvent> = ArrayList()

        this.context?.database?.use {
            select("FavoriteMatch")
                .whereArgs("(idEvent = {id_event})",
                    "id_event" to "${idEvent}").exec {
                    parseList(object : MapRowParser<MutableList<LocalEvent>> {
                        override fun parseRow(columns: Map<String, Any?>): MutableList<LocalEvent> {
                            val evt = LocalEvent(
                                columns["idEvent"].toString()
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
                "FavoriteMatch",
                "idEvent" to event?.eventId,
                "idHomeTeam" to event?.homeId,
                "idAwayTeam" to event?.awayId,
                "strHomeTeam" to event?.homeTeam,
                "strAwayTeam" to event?.awayTeam,
                "intHomeScore" to event?.homeScore,
                "intAwayScore" to event?.awayScore,
                "dateEvent" to event?.eventDate
            )
            context?.longToast("Match ditambahkan ke Favorit")
        }
    }

    fun deleteFavoriteMatch(idEvent: String?) {
        context?.database?.use {
            delete("FavoriteMatch", "idEvent = {id_event}", "id_event" to "${idEvent}")
            context.longToast("Match dihapus dari Favorit")
        }
    }
}