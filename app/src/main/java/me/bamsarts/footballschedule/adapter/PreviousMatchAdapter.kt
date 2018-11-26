package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.bamsarts.footballschedule.model.Match
import org.jetbrains.anko.find
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.utils.formatDate


class PreviousMatchAdapter(private val matchs: MutableList<Match>, private val listener: (Match) -> Unit) : RecyclerView.Adapter<PreviousMatchAdapter.PreviousMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviousMatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_match, parent, false)
        return PreviousMatchViewHolder(view)
    }

    override fun getItemCount(): Int  = matchs.size

    override fun onBindViewHolder(holder: PreviousMatchViewHolder, viewType: Int) {
        holder.bindItem(matchs[viewType], listener)
    }

    class PreviousMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val eventDate: TextView = view.find(R.id.date)
        val homeName: TextView = view.find(R.id.homeTeamName)
        val awayName: TextView = view.find(R.id.awayTeamName)
        val awayScore: TextView = view.find(R.id.awayscore)
        val homeScore:TextView = view.find(R.id.homeScore)


        fun bindItem(event: Match, listener: (Match) -> Unit) {

            eventDate.text = event.eventDate?.formatDate()
            homeName.text = event.homeTeam
            awayName.text = event.awayTeam
            awayScore.text = event.awayScore?: "-"
            homeScore.text = event.homeScore?: "-"

            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}