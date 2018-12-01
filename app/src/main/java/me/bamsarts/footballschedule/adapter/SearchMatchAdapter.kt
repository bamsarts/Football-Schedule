package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.model.Match
import org.jetbrains.anko.*

class SearchMatchAdapter(private val match: List<Match>, private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<SearchMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SearchMatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_match, parent, false)
        return SearchMatchViewHolder(view)
    }

    override fun getItemCount(): Int = match.size

    override fun onBindViewHolder(p0: SearchMatchViewHolder, viewType: Int) {
        p0.bindItem(match[viewType], listener)
    }

}

class SearchMatchViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val scoreHome: TextView = view.find(R.id.homeScore)
    private val nameTeamHome: TextView = view.find(R.id.homeTeamName)
    private val scoreAway: TextView = view.find(R.id.awayscore)
    private val dateMatch: TextView = view.find(R.id.date)
    private val nameTeamAway: TextView = view.find(R.id.awayTeamName)

    fun bindItem(matches: Match, listener: (Match) -> Unit){
        scoreHome.text = matches.homeScore?: "-"
        nameTeamHome.text = matches.homeTeam
        scoreAway.text = matches.awayScore?: "-"
        dateMatch.text = matches.dateEvent
        nameTeamAway.text = matches.awayTeam

        itemView.setOnClickListener{ listener(matches) }
    }
}