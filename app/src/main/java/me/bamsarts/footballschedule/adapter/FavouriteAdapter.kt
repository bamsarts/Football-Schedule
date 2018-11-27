package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.bamsarts.footballschedule.db.FavouriteData
import me.bamsarts.footballschedule.R
import org.jetbrains.anko.*


class FavouriteAdapter(private val favorite:List<FavouriteData>,
                       private val listener:(FavouriteData) -> Unit)
    : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>(){

    class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val eventDate: TextView = view.find(R.id.date)
        private val homeName: TextView = view.find(R.id.homeTeamName)
        private val homeScore: TextView = view.find(R.id.homeScore)
        private val awayName: TextView = view.find(R.id.awayTeamName)
        private val awayScore: TextView = view.find(R.id.awayscore)

        fun bindItem(favorite: FavouriteData, listener: (FavouriteData) -> Unit){

            eventDate.text = favorite.DATE_EVENT
            homeName.text = favorite.strHomeTeam
            homeScore.text = favorite.intHomeScore?: "-"
            awayName.text = favorite.strAwayTeam
            awayScore.text = favorite.intAwayScore?: "-"

            itemView.setOnClickListener{ listener(favorite) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_match, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}



