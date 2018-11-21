package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.bamsarts.footballschedule.DB.Favorite
import me.bamsarts.footballschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class FavoriteRecyclerViewAdapter(private val favorite:List<Favorite>,
                                  private val listener:(Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteRecyclerViewAdapter.FavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_match, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val eventDate: TextView = view.find(R.id.daftar_date_event)
        private val homeName: TextView = view.find(R.id.daftar_home_name)
        private val homeScore: TextView = view.find(R.id.daftar_home_score)
        private val awayName: TextView = view.find(R.id.daftar_away_name)
        private val awayScore: TextView = view.find(R.id.daftar_away_score)

        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit){

            eventDate.text = favorite.dateEvent
            homeName.text = favorite.strHomeTeam
            homeScore.text = favorite.intHomeScore
            awayName.text = favorite.strAwayTeam
            awayScore.text = favorite.intAwayScore

            itemView.onClick { listener(favorite) }
        }
    }


}



