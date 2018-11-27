package me.bamsarts.footballschedule.apis

import android.net.Uri
import me.bamsarts.footballschedule.BuildConfig

object TheSportDB {

    fun getEventDetail(eventId: String): String{
        return BuildConfig.BASE_URL +"api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + eventId
    }

    fun getTeamDetail(teamId: String?) : String{
        return BuildConfig.BASE_URL +"api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }

    fun getPreviousMatch(matchId: String?): String{
        return BuildConfig.BASE_URL +"api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + matchId
    }

    fun getNextMatch(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id", "4328")
            .build()
            .toString()
    }

}