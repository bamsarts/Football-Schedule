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


class PrevRecyclerViewAdapter(private val list: MutableList<Match>, private val listener: (Match) -> Unit) : RecyclerView.Adapter<PrevRecyclerViewAdapter.PrevViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PrevViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_list_match, p0, false)
        return PrevViewHolder(view)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(p0: PrevViewHolder, p1: Int) {
        p0.bindItem(list[p1], listener)
    }

    class PrevViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val homeName = view.find<TextView>(R.id.homeTeamName)
        val eventDate: TextView = view.find(R.id.date)
        val homeScore = view.find<TextView>(R.id.homeScore)
        val awayName = view.find<TextView>(R.id.awayTeamName)
        val awayScore = view.find<TextView>(R.id.awayscore)


        fun bindItem(e: Match, listener: (Match) -> Unit) {

            homeName.text = e.homeTeam
            eventDate.text = e.eventDate?.formatDate()
            homeScore.text = e.homeScore
            awayName.text = e.awayTeam
            awayScore.text = e.awayScore

            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}