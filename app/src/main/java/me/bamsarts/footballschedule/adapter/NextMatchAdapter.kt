package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.utils.formatDate
import org.jetbrains.anko.find

class NextMatchAdapter(private val matchs: MutableList<Match>, private val listener: (Match) -> Unit) : RecyclerView.Adapter<NextMatchAdapter.NextMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_match, parent, false)
        return NextMatchViewHolder(view)
    }

    override fun getItemCount(): Int  = matchs.size

    override fun onBindViewHolder(parent: NextMatchViewHolder, viewType: Int) {
        parent.bindItem(matchs[viewType], listener)
    }

    class NextMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventDate: TextView = view.find(R.id.date)
        val homeName = view.find<TextView>(R.id.homeTeamName)
        val awayName = view.find<TextView>(R.id.awayTeamName)
        val awayScore = view.find<TextView>(R.id.awayscore)
        val homeScore = view.find<TextView>(R.id.homeScore)

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