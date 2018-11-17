package me.bamsarts.footballschedule.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.model.Match
import org.jetbrains.anko.find

class NextRecyclerViewAdapter(private val list: MutableList<Match>, private val listener: (Match) -> Unit) : RecyclerView.Adapter<NextRecyclerViewAdapter.NextViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NextViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.eventlist_item, p0, false)
        return NextViewHolder(view)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(p0: NextViewHolder, p1: Int) {
        p0.bindItem(list[p1], listener)
    }

    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeName = view.find<TextView>(R.id.daftar_home_name)
        val eventDate: TextView = view.find(R.id.daftar_date_event)
        val homeScore = view.find<TextView>(R.id.daftar_home_score)
        val awayName = view.find<TextView>(R.id.daftar_away_name)
        val awayScore = view.find<TextView>(R.id.daftar_away_score)

        fun bindItem(e: Match, listener: (Match) -> Unit) {
            homeName.text = e.homeTeam
            eventDate.text = e.eventDate
            homeScore.text = e.homeScore ?: "0"
            awayName.text = e.awayTeam
            awayScore.text = e.awayScore ?: "0"

            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}