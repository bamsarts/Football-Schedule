package me.bamsarts.footballschedule.DB

data class FavouriteData(val ID_EVENT: String?,
                         val strHomeTeam: String?,
                         val strAwayTeam: String?,
                         val intHomeScore: String? = null,
                         val intAwayScore: String? = null,
                         val DATE_EVENT: String?,
                         val strDate: String?,
                         val idHomeTeam: String?,
                         val idAwayTeam: String?){

    companion object {
        const val TABLE_FAVOURITE_MATCH: String = "TABLE_FAVOURITE_MATCH"
        const val ID: String = "id"
        const val ID_EVENT: String = "ID_EVENT"
        const val idHomeTeam: String = "idHomeTeam"
        const val idAwayTeam: String = "idAwayTeam"
        const val strHomeTeam: String = "strHomeTeam"
        const val strAwayTeam: String = "strAwayTeam"
        const val intHomeScore: String = "intHomeScore"
        const val intAwayScore: String = "intAwayScore"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val strDate: String = "strDate"
    }
}