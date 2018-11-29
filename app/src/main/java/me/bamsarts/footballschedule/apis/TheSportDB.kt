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

    fun getNextMatch(matchId: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id", matchId)
            .build()
            .toString()
    }

    fun getMatchSearch(teamName: String?): String{
        return BuildConfig.BASE_URL +"api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + teamName
    }

    fun getTeams(league: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("search_all_teams.php")
            .appendQueryParameter("l", league)
            .build()
            .toString()
    }

    fun getTeamByName(teamName: String?):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("searchteams.php")
            .appendQueryParameter("t",teamName)
            .build()
            .toString()
    }

    fun getAllPlayer(teamId: String?):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookup_all_players.php")
            .appendQueryParameter("id",teamId)
            .build()
            .toString()
    }

}