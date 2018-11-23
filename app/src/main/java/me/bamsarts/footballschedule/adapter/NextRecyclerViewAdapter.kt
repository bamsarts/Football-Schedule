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

class NextRecyclerViewAdapter(private val list: MutableList<Match>, private val listener: (Match) -> Unit) : RecyclerView.Adapter<NextRecyclerViewAdapter.NextViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NextViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_list_match, p0, false)
        return NextViewHolder(view)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(p0: NextViewHolder, p1: Int) {
        p0.bindItem(list[p1], listener)
    }

    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeName = view.find<TextView>(R.id.homeTeamName)
        val eventDate: TextView = view.find(R.id.date)
        val homeScore = view.find<TextView>(R.id.homeScore)
        val awayName = view.find<TextView>(R.id.awayTeamName)
        val awayScore = view.find<TextView>(R.id.awayscore)

        fun bindItem(e: Match, listener: (Match) -> Unit) {
            homeName.text = e.homeTeam
            eventDate.text = e.eventDate?.formatDate()
            homeScore.text = e.homeScore ?: "0"
            awayName.text = e.awayTeam
            awayScore.text = e.awayScore ?: "0"

            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}