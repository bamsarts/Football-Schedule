package me.bamsarts.footballschedule.api

import me.bamsarts.footballschedule.model.MatchesResponses
import me.bamsarts.footballschedule.model.TeamResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportDBApi {
    @GET("/api/v1/json/1/eventspastleague.php?id=4328")
    fun getPrevMatch(): Call<MatchesResponses>

    @GET("/api/v1/json/1/eventsnextleague.php?id=4328")
    fun getNextMatch(): Call<MatchesResponses>

    @GET("api/v1/json/1/lookupteam.php")
    fun getClubDetailById(@Query("id") id: String?): Call<TeamResponses>

    @GET("api/v1/json/1/lookupevent.php")
    fun getEventDetailById(@Query("id") id: String?): Call<MatchesResponses>

}