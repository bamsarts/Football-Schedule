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
import me.bamsarts.footballschedule.model.Player
import org.jetbrains.anko.*

class PlayersAdapter(private val player: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {

        holder.bindItem(player[position], listener)
    }

    override fun getItemCount(): Int = player.size

}


class PlayerViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view){

    private val playerImage: ImageView = view.find(R.id.imgPlayer)
    private val playerName: TextView = view.find(R.id.namePlayer)

    fun bindItem(player: Player, listener: (Player) -> Unit) {

        if(player.strCutout != null)
            Glide.with(itemView.context).load(player.strCutout).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(playerImage)
        else
            Glide.with(itemView.context).load(player.strThumb).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(playerImage)

        playerName.text = player.strPlayer
        itemView.setOnClickListener { listener(player) }
    }
}