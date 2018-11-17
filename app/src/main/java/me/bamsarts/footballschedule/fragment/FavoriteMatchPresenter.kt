package me.bamsarts.footballschedule.fragment

import android.content.Context
import me.bamsarts.footballschedule.model.LocalEvent
import me.bamsarts.footballschedule.utils.database
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class FavoriteMatchPresenter(private val context: Context?, private val view: FavoriteMatchContract.View?){

    fun fetchFavMatches(list: MutableList<LocalEvent>) {
        this.view?.showProgress(true)
        this.context?.database?.use {
            select("FavoriteMatch").exec {
                parseList(object : MapRowParser<MutableList<LocalEvent>> {
                    override fun parseRow(columns: Map<String, Any?>): MutableList<LocalEvent> {
                        val evt = LocalEvent(
                            idEvent = columns["idEvent"].toString(),
                            idSoccerXML = columns["idSoccerXML"].toString(),
                            idHomeTeam = columns["idHomeTeam"].toString(),
                            strHomeTeam = columns["strHomeTeam"].toString(),
                            idAwayTeam = columns["idAwayTeam"].toString(),
                            strAwayTeam = columns["strAwayTeam"].toString(),
                            intHomeScore = if (columns["intHomeScore"].toString() != "null") columns["intHomeScore"].toString() else "",
                            intAwayScore = if (columns["intAwayScore"].toString() != "null") columns["intAwayScore"].toString() else "",
                            dateEvent = columns["dateEvent"].toString(),
                            strDate = columns["strDate"].toString()
                        )

                        list.add(evt)

                        return list
                    }
                })
            }

            this@FavoriteMatchPresenter.view?.showProgress(false)
        }
    }
}