package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.model.Player
import org.jetbrains.anko.*

class PlayersAdapter(private val player: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayersUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }

    override fun getItemCount(): Int = player.size

}

class PlayersUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.player_image
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }

}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val playerImage: ImageView = view.find(R.id.player_image)
    private val playerName: TextView = view.find(R.id.player_name)

    fun bindItem(player: Player, listener: (Player) -> Unit) {
        if (player.strCutout != null) Picasso.get().load(player.strCutout).into(playerImage) else Picasso.get().load(player.strThumb).into(playerImage)

//        Glide.with(this).load(player.strCutout).into(playerImage)
        playerName.text = player.strPlayer
        itemView.setOnClickListener { listener(player) }
    }
}