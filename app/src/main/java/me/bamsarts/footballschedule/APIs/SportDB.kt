package me.bamsarts.footballschedule.APIs

import android.net.Uri
import me.bamsarts.footballschedule.model.MatchesResponses
import me.bamsarts.footballschedule.model.TeamResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import me.bamsarts.footballschedule.BuildConfig

interface SportDB {
    @GET("/api/v1/json/1/eventspastleague.php?id=4328")
    fun getPrevMatch(): Call<MatchesResponses>

    @GET("/api/v1/json/1/eventsnextleague.php?id=4328")
    fun getNextMatch(): Call<MatchesResponses>

    @GET("api/v1/json/1/lookupteam.php")
    fun getClubDetailById(@Query("id") id: String?): Call<TeamResponses>

}

object TheSportDB {
    fun getEventDetail(eventId: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupevent.php")
            .appendQueryParameter("id", eventId.toString())
            .build()
            .toString()
    }
}