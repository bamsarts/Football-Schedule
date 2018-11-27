package me.bamsarts.footballschedule.presenter

import android.content.Context
import me.bamsarts.footballschedule.db.FavouriteData
import me.bamsarts.footballschedule.db.database
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class FavoriteMatchPresenter(private val context: Context?){

    fun fetchFavMatches(list: MutableList<FavouriteData>) {
        this.context?.database?.use {
            select(FavouriteData.TABLE_FAVOURITE_MATCH).exec {
                parseList(object : MapRowParser<MutableList<FavouriteData>> {
                    override fun parseRow(columns: Map<String, Any?>): MutableList<FavouriteData> {
                        val evt = FavouriteData(
                            ID_EVENT = columns["ID_EVENT"].toString(),
                            idHomeTeam = columns["idHomeTeam"].toString(),
                            strHomeTeam = columns["strHomeTeam"].toString(),
                            idAwayTeam = columns["idAwayTeam"].toString(),
                            strAwayTeam = columns["strAwayTeam"].toString(),
                            intHomeScore = if (columns["intHomeScore"].toString() != "null") columns["intHomeScore"].toString() else "-",
                            intAwayScore = if (columns["intAwayScore"].toString() != "null") columns["intAwayScore"].toString() else "-",
                            DATE_EVENT = columns["DATE_EVENT"].toString(),
                            strDate = columns["strDate"].toString()
                        )

                        list.add(evt)

                        return list
                    }
                })
            }

            this@FavoriteMatchPresenter
        }
    }
}