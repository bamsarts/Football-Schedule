package me.bamsarts.footballschedule.DB

data class FavouriteData(val idEvent: String?,
                         val idSoccerXML: String?,
                         val strHomeTeam: String?,
                         val strAwayTeam: String?,
                         val intHomeScore: String?,
                         val intAwayScore: String?,
                         val dateEvent: String?,
                         val strDate: String?,
                         val idHomeTeam: String?,
                         val idAwayTeam: String?){

    companion object {
        const val FavoriteMatch: String = "FavoriteMatch"
        const val id: String = "id"
        const val idEvent: String = "idEvent"
        const val idSoccerXML: String = "idSoccerXML"
        const val idHomeTeam: String = "idHomeTeam"
        const val idAwayTeam: String = "idAwayTeam"
        const val strHomeTeam: String = "strHomeTeam"
        const val strAwayTeam: String = "strAwayTeam"
        const val intHomeScore: String = "intHomeScore"
        const val intAwayScore: String = "intAwayScore"
        const val dateEvent: String = "dateEvent"
        const val strDate: String = "strDate"
    }
}