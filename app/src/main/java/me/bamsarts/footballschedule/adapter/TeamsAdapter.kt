package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.R.id.*
import me.bamsarts.footballschedule.model.Team
import org.jetbrains.anko.*

class TeamsAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_club, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val nameTeam: TextView = view.find(clubName)
    private val badgeTeam: ImageView = view.find(clubBadge)

    fun bindItem(teams: Team, listener: (Team) -> Unit) {

        Glide.with(itemView.context).load(teams.strTeamBadge).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(badgeTeam)
        nameTeam.text = teams.strTeam
        itemView.setOnClickListener { listener(teams) }
    }
}